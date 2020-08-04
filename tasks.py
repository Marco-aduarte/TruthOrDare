from invoke import task

@task
def commit(c,message):
    if len(message)<3:
        print("Porra felix, tens de meter a messagem maior pha... e tao pequeno que me faz lembrar algo")
    else:
        c.run("git add .")
        c.run(f"git commit -am {message}")

@task
def pull(c,branch):
    if not branch:
        print("Felix tens de meter o nome do teu branch...besta")
    else:
        c.run("git checkout master")
        c.run("git pull")
        c.run("git checkout "+branch)
        c.run("git merge master")
        c.run("git checkout "+branch)
        c.run("git push")
@task
def push(c,branch):
    if not branch:
        print("Felix tens de meter o nome do teu branch...besta")
        exit(0)
    x=input("Já fizeste o commit? (y/n)")
    if not x == "y":
        exit(0)
    c.run(f"git checkout {branch}")
    c.run("git push")
#oI

@task
def chapada(c, branch):
    c.run("git fetch --all")
    c.run("git reset --hard origin/master")
    if branch:
        c.run(f"git reset --hard origin/{branch}")