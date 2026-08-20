# UI Test Plan

- Working directory: project root
- Setup command: `javac -encoding UTF-8 -d out\ui-test src\main\java\*.java`
- Comparison: exact text after normalizing line endings and trimming trailing spaces from each line

## Test Cases

### TC-01: Exit immediately

Aim: Verify that Sophon greets the user and exits cleanly when the user enters `bye`.

Command:
```text
java -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -cp out\ui-test Sophon
```

Inputs:
```text
bye
```

Expected output:
```text
____________________________________________________________
 ____              _
/ ___|  ___  _ __ | |__   ___  _ __
\___ \ / _ \| '_ \| '_ \ / _ \| '_ \
 ___) | (_) | |_) | | | | (_) | | | |
|____/ \___/| .__/|_| |_|\___/|_| |_|
            |_|
     你好! I'm Sophon.
     I'm listening.
     What do you wish to communicate?
____________________________________________________________
____________________________________________________________
     Our conversation ends here.
     Until we meet again.
____________________________________________________________
```

### TC-02: Add and list a task

Aim: Verify that Sophon records a task and displays it in the task list.

Command:
```text
java -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -cp out\ui-test Sophon
```

Inputs:
```text
read book
list
bye
```

Expected output:
```text
____________________________________________________________
 ____              _
/ ___|  ___  _ __ | |__   ___  _ __
\___ \ / _ \| '_ \| '_ \ / _ \| '_ \
 ___) | (_) | |_) | | | | (_) | | | |
|____/ \___/| .__/|_| |_|\___/|_| |_|
            |_|
     你好! I'm Sophon.
     I'm listening.
     What do you wish to communicate?
____________________________________________________________
____________________________________________________________
     added: read book
____________________________________________________________
____________________________________________________________
     Current tasks under observation:
     1.[ ] read book
____________________________________________________________
____________________________________________________________
     Our conversation ends here.
     Until we meet again.
____________________________________________________________
```
