#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <sys/time.h>
#include <stdarg.h>

#define BUFFER_SIZE 4096
#define GET_INFO_INTERVAL 2000 //us
//modify from hust_oj
long long int get_proc_status(int pid, const char *mark){
	FILE *pf;
	char fn[BUFFER_SIZE], buf[BUFFER_SIZE];
	long long int ret = 0;
	sprintf(fn, "/proc/%d/status", pid);
	pf = fopen(fn, "re");
	if(pf == NULL){
		return -1;
	}
	int m = strlen(mark);
	while (pf && fgets(buf, BUFFER_SIZE - 1, pf)){
		buf[strlen(buf) - 1] = 0;
		if (strncmp(buf, mark, m) == 0){
			if(1!=sscanf(buf + m + 1, "%lld", &ret)) printf("proc read fail\n");
		}
	}
	if (pf) fclose(pf);
	return ret;
}
long long int get_real_memory_usage(int pid){
    long long int vm_rss = get_proc_status(pid,"VmRSS:");
    long long int vm_swap = get_proc_status(pid,"VmSwap:");
	if(vm_rss == -1 || vm_swap == -1){
		return -1;
	}
    return vm_rss + vm_swap;
}
int kb_to_mb(long long int kb){
	if(kb%1024!=0){
		return kb/1024+1;
	}else{
		return kb/1024;
	}
}
//hust_oj
int execute_cmd(const char *fmt, ...)   {//执行命令获得返回值
	char cmd[BUFFER_SIZE];

	int ret = 0;
	va_list ap;

	va_start(ap, fmt);
	vsprintf(cmd, fmt, ap);
	ret = system(cmd);
	va_end(ap);
	return ret;
}
int write_result(int mb,char* path){
	FILE* fp = fopen(path,"w");
	char result[BUFFER_SIZE];

	if(fp == NULL){
		return 2;
	}else{
		sprintf(result,"%d",mb);
		if(!fwrite(result,sizeof(char),strlen(result)/sizeof(char),fp)){
			return 2;
		}
	}
	if(fp) fclose(fp);
	return 0;
}
int main(int argc,char* argv[]){
	//return 0 normal 1 process no found 2 file system error
    int pid = atoi(argv[1]);
    int memory_limit_mb = atoi(argv[2]);
	char* file_path = argv[3];

	long long int memory_limit_kb = memory_limit_mb*1024;
	long long int max_memory_kb = 0;

    while(1){
		long long int actual_memory_kb = get_real_memory_usage(pid);

		if(actual_memory_kb == -1){
			//process no found
			return write_result(kb_to_mb(max_memory_kb),file_path);
		}

		int actual_memory_mb = kb_to_mb(actual_memory_kb);

		if(actual_memory_kb > max_memory_kb){
			max_memory_kb = actual_memory_kb;
			if(actual_memory_kb>memory_limit_kb){
				//kill_process
				char cmd[BUFFER_SIZE];
				sprintf(cmd,"kill -9 %d",pid);
				execute_cmd(&cmd);
				return write_result(kb_to_mb(max_memory_kb),file_path);
			}
		}
		usleep(GET_INFO_INTERVAL);
	}
}