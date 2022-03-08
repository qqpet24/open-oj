#include <unistd.h>
//https://chromium.googlesource.com/chromiumos/docs/+/master/constants/syscalls.md
//https://code.woboq.org/qt5/include/unistd.h.html
//https://github.com/gaoze1998/soj
//sudo gcc -c -fPIC fake.c -o fake.o -O2 -std=gnu99
//sudo gcc fake.o -shared -o fake.so
//LD_PRELOAD=./fake.so 必须放在执行命令前，否则无效
pid_t fork(void) {
    exit(1);
}

int execv(const char *pathname, char * const argv[]) {
    exit(1);
}

int clone(int (*fn)(void *), void *child_stack, int flags, void *arg) {
    exit(1);
}

int kill(pid_t pid,int signo) {
    exit(1);
}

pid_t vfork(void) {
    exit(1);
}