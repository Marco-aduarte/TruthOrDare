import invoke

@task
def commit(c,message):
    if not message:
        print("Porra Félix tens de meter uma mensagem no commit...")
    else:
        c.run("git add .")
        c.run(f"git commit -am {message}")

@task
def merge(c,branch):
    if not branch:
        print("Felix tens de meter o nome do teu branch...besta")
    else:
        c.run("git checkout master")
        c.run("git pull")
        c.run("git checkout "+branch)
        c.run("git merge master")
        c.run("git checkout "+branch)
        c.run("git push")
        