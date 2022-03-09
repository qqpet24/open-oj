#include <unistd.h>
#include <sys/stat.h>
#include <sys/select.h>
#include <stdio.h>
//https://chromium.googlesource.com/chromiumos/docs/+/master/constants/syscalls.md
//https://www.cnblogs.com/luo77/p/5823241.html
//sudo gcc -c -fPIC fakev2.c -o fakev2.o -O2 -std=gnu99 -w
//sudo gcc fakev2.o -shared -o fakev2.so -w
//LD_PRELOAD=./fakev2.so 必须放在执行命令前，否则无效

//process
pid_t fork(void){exit(1);}
int clone(int (*fn)(void *), void *stack, int flags, void *arg, .../* pid_t *parent_tid, void *tls, pid_t *child_tid */ ){exit(1);}
int execve(const char *pathname, char *const argv[],char *const envp[]){exit(1);}
int execveat(int dirfd, const char *pathname,const char *const argv[], const char *const envp[],int flags){exit(1);}
//long ptrace(enum __ptrace_request request, pid_t pid, void * addr, void * data){exit(1);}
int sched_setparam(pid_t pid, const struct sched_param *param){exit(1);}
int sched_setscheduler(pid_t pid, int policy,const struct sched_param *param){exit(1);}
pid_t vfork(void){exit(1);}
pid_t setsid(void){exit(1);}
int kill(pid_t pid, int sig){exit(1);}

//file
int creat(const char *pathname, mode_t mode){exit(1);}
//write hustoj中不知道为什么可以
ssize_t write(int fd, const void *buf, size_t count){exit(1);}
ssize_t writev(int fd, const struct iovec *iov, int iovcnt){exit(1);}
ssize_t pwrite(int fd, const void *buf, size_t count, off_t offset){exit(1);}
ssize_t pwritev(int fd, const struct iovec *iov, int iovcnt, off_t offset){exit(1);}
ssize_t pwritev2(int fd, const struct iovec *iov, int iovcnt, off_t offset, int flags){exit(1);}
int flock(int fd, int operation){exit(1);}
int truncate(const char *path, off_t length){exit(1);}
int ftruncate(int fd, off_t length){exit(1);}
int fsync(int fd){exit(1);}
int fdatasync(int fd){exit(1);}
mode_t umask(mode_t mask){exit(1);}
//file auth
int chdir(const char *path){exit(1);}
int fchdir(int fd){exit(1);}
int chmod(const char *pathname, mode_t mode){exit(1);}
int fchmod(int fd, mode_t mode){exit(1);}
int chown(const char *pathname, uid_t owner, gid_t group){exit(1);}
int fchown(int fd, uid_t owner, gid_t group){exit(1);}
int lchown(const char *pathname, uid_t owner, gid_t group){exit(1);}
int chroot(const char *path){exit(1);}
int stat(const char *restrict pathname,struct stat *restrict statbuf){exit(1);}
int fstat(int fd, struct stat *statbuf){exit(1);}
int lstat(const char *restrict pathname, struct stat *restrict statbuf){exit(1);}
int statfs(const char *path, struct statfs *buf){exit(1);}
int fstatfs(int fd, struct statfs *buf){exit(1);}
ssize_t getdents64(int fd, void *dirp, size_t count){exit(1);}
int mkdir(const char *pathname, mode_t mode){exit(1);}
int mknod(const char *pathname, mode_t mode, dev_t dev){exit(1);}
int rmdir(const char *pathname){exit(1);}
int rename(const char *oldpath, const char *newpath){exit(1);}
int remove(const char *pathname){exit(1);}
int removexattr(const char *path, const char *name){exit(1);}
int lremovexattr(const char *path, const char *name){exit(1);}
int fremovexattr(int fd, const char *name){exit(1);}
int link(const char *oldpath, const char *newpath){exit(1);}
int unlink(const char *pathname){exit(1);}
int unlinkat(int dirfd, const char *pathname, int flags);
int symlink(const char *target, const char *linkpath){exit(1);}
int umount(const char *target){exit(1);}
int umount2(const char *target, int flags){exit(1);}
int mount(const char *source, const char *target,const char *filesystemtype, unsigned long mountflags,const void *data){exit(1);}
int utime(const char *filename, const struct utimbuf *times){exit(1);}
int utimes(const char *filename, const struct timeval times[2]){exit(1);}
int ustat(dev_t dev, struct ustat *ubuf){exit(1);}
int close(int fildes){exit(1);}
int ioctl(int fildes,int cmd,...){exit(1);}

//system control
int setrlimit(int resource, const struct rlimit *rlim){exit(1);}
int ioperm(unsigned long from, unsigned long num, int turn_on){exit(1);}
int reboot(int cmd){exit(1);}
int swapon(const char *path, int swapflags){exit(1);}
int swapoff(const char *path){exit(1);}
int sysinfo(struct sysinfo *info){exit(1);}
int adjtimex(struct timex *buf){exit(1);}
int settimeofday(const struct timeval *tv,const struct timezone *tz){exit(1);}
unsigned int alarm(unsigned int seconds){exit(1);}
int msync(void *addr, size_t length, int flags){exit(1);}
void sync(void){exit(1);}

//network
int socket(int domain, int type, int protocol){exit(1);}
int bind(int sockfd, const struct sockaddr *addr,socklen_t addrlen){exit(1);}
int connect(int sockfd, const struct sockaddr *addr,socklen_t addrlen){exit(1);}
int accept(int sockfd, struct sockaddr *restrict addr,socklen_t *restrict addrlen){exit(1);}
ssize_t send(int sockfd, const void *buf, size_t len, int flags){exit(1);}
ssize_t sendto(int sockfd, const void *buf, size_t len, int flags,const struct sockaddr *dest_addr, socklen_t addrlen){exit(1);}
ssize_t sendmsg(int sockfd, const struct msghdr *msg, int flags){exit(1);}
ssize_t recv(int sockfd, void *buf, size_t len, int flags){exit(1);}
ssize_t recvfrom(int sockfd, void *restrict buf, size_t len, int flags,struct sockaddr *restrict src_addr,socklen_t *restrict addrlen){exit(1);}
ssize_t recvmsg(int sockfd, struct msghdr *msg, int flags){exit(1);}
int shutdown(int sockfd, int how){exit(1);}
int select(int nfds, fd_set *restrict readfds,fd_set *restrict writefds, fd_set *restrict exceptfds,struct timeval *restrict timeout){exit(1);}
int setsockopt(int sockfd, int level, int optname,const void *optval, socklen_t optlen){exit(1);}
ssize_t sendfile(int out_fd, int in_fd, off_t *offset, size_t count){exit(1);}
int socketpair(int domain, int type, int protocol, int sv[2]){exit(1);}

//user
int setuid(uid_t uid){exit(1);}
int setgid(gid_t gid){exit(1);}
int setreuid(uid_t ruid, uid_t euid){exit(1);}
int setregid(gid_t rgid, gid_t egid){exit(1);}
int setresuid(uid_t ruid, uid_t euid, uid_t suid){exit(1);}
int setresgid(gid_t rgid, gid_t egid, gid_t sgid){exit(1);}
int setfsgid(gid_t fsgid){exit(1);}
int setfsuid(uid_t fsuid){exit(1);}
int getgroups(int size, gid_t list[]){exit(1);}

//后续添加,文件读写删除其实加这三个即可,这三个限制了,文件都打不开
int open(const char *pathname, int flags, ...){exit(1);}
int open64(const char *pathname, int flags, ...){exit(1);}
FILE *fopen(const char *pathname, const char *mode){exit(1);}