//gcc main.c -o main -O2 -lm -std=c99 -lpthread -w
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <sys/time.h>
#include <sys/wait.h>
#include <stdarg.h>
#include <sys/resource.h>
#include <pthread.h>
#include <signal.h>

#define BUFFER_SIZE 16384 //byte

struct kill_process_handler_args{
    int pid;
    int s;
};
void* kill_process_handler(void* args){
    int pid = ((struct kill_process_handler_args*)args)->pid;
    int s = ((struct kill_process_handler_args*)args)->s;
    if (pthread_detach(pthread_self()) == 0) {
        sleep(s);
        kill(pid, 31);
    }
}
int before_compare_return(int flag,FILE* file1,FILE* file2){
    if(file1) fclose(file1);
    if(file2) fclose(file2);
    return flag;
}
int compare(char* file1,char* file2){
    FILE *f1, *f2;
	f1 = fopen(file1, "re");
	f2 = fopen(file2, "re");
    if(!f1 || !f2){
        return before_compare_return(2,f1,f2);//RE
    }
    int l1=0,l2=0;
    char buffer1[BUFFER_SIZE];
    char buffer2[BUFFER_SIZE];

    do{
        l1 = fread(buffer1,sizeof(buffer1),1,f1);
        l2 = fread(buffer2,sizeof(buffer2),1,f2);
        if(strlen(buffer1)!=strlen(buffer2)) return before_compare_return(1,f1,f2);
        for (int i = 0; i < strlen(buffer1); i++) {
            if (buffer1[i] != buffer2[i]) {
                return before_compare_return(1,f1,f2);//WA
            }
        }
    }while(l1==sizeof(buffer1) || l2==sizeof(buffer2));
    return before_compare_return(0,f1,f2);//AC
}
int main(int argc,char* argv[]){
    int cpu = 500;//ms
    int memory = 50;//mb
    int max_file_size = 5;//mb;
    char* test_in_path = "./1.in";
    char* standard_out_path = "./1.out";
    char* test_out_path = "tmp.out";
    char* test_error_path = "error.out";

    char* const envp[2] = {"LD_PRELOAD=/tools/fakev2.so",NULL};

    int pid = fork();

    if(pid == 0){
        //child
        struct rlimit tmp_rlimit;

        //cpu s,exceed rlim_cur->SIGXCPU exceed rlim_max->SIGKILL
        tmp_rlimit.rlim_cur = cpu/1000+1;
        tmp_rlimit.rlim_max = cpu/1000+2;
        setrlimit(RLIMIT_CPU, &tmp_rlimit);

        //mem b SIGSEGV
        int tmp_mem = memory<<20;
        tmp_rlimit.rlim_cur = tmp_mem;
        tmp_rlimit.rlim_max = tmp_mem+256;
        setrlimit(RLIMIT_AS, &tmp_rlimit);

        //file b SIGXFSZ
        int tmp_fsize = max_file_size<<20;
        tmp_rlimit.rlim_cur = tmp_fsize;
        tmp_rlimit.rlim_max = tmp_fsize+256<<20;
        setrlimit(RLIMIT_FSIZE, &tmp_rlimit);

        // RLIMIT_NPROC
        tmp_rlimit.rlim_cur = 1;
        tmp_rlimit.rlim_max = 1;
        setrlimit(RLIMIT_NPROC,&tmp_rlimit);

        //int result = execute_cmd("./test <1.in > tmp.out 2>&1");
        stdin=freopen(test_in_path, "r", stdin);
        stdout=freopen(test_out_path, "w", stdout);
	    stderr=freopen(test_error_path, "a+", stderr);
        execle("./test","./test",(char*) NULL,envp);
    }else{
        //parent
        pthread_t thread1;
        unsigned int max_run_time_s = (cpu/1000+(cpu%1000>0?1:0))*2;
        struct kill_process_handler_args args;
        args.pid = pid;
        args.s = max_run_time_s;
        pthread_create( &thread1, NULL, kill_process_handler, (void*) &args);

        struct rusage child_rusage;
        int status = 0;

        waitpid(pid,&status,0);
        getrusage(RUSAGE_CHILDREN, &child_rusage);

        unsigned int total_runtime_us = (child_rusage.ru_utime.tv_sec*1000*1000+child_rusage.ru_utime.tv_usec+child_rusage.ru_stime.tv_sec*1000*1000+child_rusage.ru_stime.tv_usec);
        unsigned int total_runtime_ms = total_runtime_us/1000+(total_runtime_us%1000>0?1:0);
        unsigned int total_memory_kb = child_rusage.ru_maxrss;
        unsigned int total_memory_mb = total_memory_kb/1024+(total_memory_kb%1024>0?1:0);
        unsigned int exitcode = WEXITSTATUS(status);
        unsigned int exitsignal = WTERMSIG(status);
        printf("ms:%d mb:%d exitcode:%d exitsignal:%d\n",total_runtime_ms,total_memory_mb,exitcode,exitsignal);

        char* FLAG = "UE";
        if(total_runtime_ms>cpu){
            FLAG = "ETLE";
        }else if(total_memory_mb>memory){
            FLAG = "MLE";
        }else if(exitsignal!=0){
            switch(exitsignal){
                case SIGXCPU:
                    FLAG = "ETLE";
                    break;
                case SIGSEGV:
                    FLAG = "MLE";
                    break;
                case SIGXFSZ:
                    FLAG = "OE";
                    break;
                case 31:// 31 in this program means real execute time out, which often caused by sleep() in user program
                    FLAG = "ETLE";
                    break;
            }
        }else if(exitcode != 0){
            FLAG = "RE";
        }else{
            int result = compare(standard_out_path,test_out_path);
            switch(result){
                case 2:
                    FLAG = "RE";
                    break;
                case 1:
                    FLAG = "WA";
                    break;
                case 0:
                    FLAG = "AC";
                    break;
            }
        }
        printf("%s",FLAG);
    }
}