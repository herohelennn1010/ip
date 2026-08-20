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

### TC-02: Add and list todos, deadlines, and events

Aim: Verify that Sophon records all three task types and displays them in the task list.

Command:
```text
java -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -cp out\ui-test Sophon
```

Inputs:
```text
todo read book
deadline return book /by Sunday
event project meeting /from Mon 2pm /to 4pm
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
     Recorded. A new task has entered observation:
       [T][ ] read book
     1 tasks are currently under observation.
____________________________________________________________
____________________________________________________________
     Recorded. A new deadline has entered observation:
       [D][ ] return book (by: Sunday)
     2 tasks are currently under observation.
____________________________________________________________
____________________________________________________________
     Recorded. A new event has entered observation:
       [E][ ] project meeting (from: Mon 2pm to: 4pm)
     3 tasks are currently under observation.
____________________________________________________________
____________________________________________________________
     Current tasks under observation:
     1.[T][ ] read book
     2.[D][ ] return book (by: Sunday)
     3.[E][ ] project meeting (from: Mon 2pm to: 4pm)
____________________________________________________________
____________________________________________________________
     Our conversation ends here.
     Until we meet again.
____________________________________________________________
```

### TC-03: Handle malformed task commands and plain tasks

Aim: Verify that Sophon does not crash on malformed typed commands, rejects tasks with missing fields, and explains unknown commands.

Command:
```text
java -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -cp out\ui-test Sophon
```

Inputs:
```text
todo
todo 
deadline
deadline return book
deadline return book /by
deadline /by Sunday
deadline /by
deadline       return book       /by       Sunday
event
event project meeting
event add /to
event project meeting /from Mon 2pm
event project meeting /from Mon 2pm /to
event /from Mon 2pm /to 4pm
event project meeting /from /to 4pm
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
     You have given me nothing to observe.
     A todo requires a description.
____________________________________________________________
____________________________________________________________
     You have given me nothing to observe.
     A todo requires a description.
____________________________________________________________
____________________________________________________________
     You have told me neither what must be done nor when.
     A deadline requires both.
____________________________________________________________
____________________________________________________________
     I know what must be done, but not when.
     Specify when it is due using /by.
____________________________________________________________
____________________________________________________________
     I see the task, but its deadline remains unknown.
     Tell me when it is due.
____________________________________________________________
____________________________________________________________
     I know when, but not what.
     Give the deadline a description.
____________________________________________________________
____________________________________________________________
     You have given me a boundary, but nothing to bind to it.
     Tell me what must be done, and when.
____________________________________________________________
____________________________________________________________
     Recorded. A new deadline has entered observation:
       [D][ ] return book (by: Sunday)
     1 tasks are currently under observation.
____________________________________________________________
____________________________________________________________
     You have told me neither what will happen nor when.
     An event requires both.
____________________________________________________________
____________________________________________________________
     I know what will happen, but not when.
     Tell me when it begins and when it ends.
____________________________________________________________
____________________________________________________________
     I see when it ends, but not when it begins.
     Tell me when it begins.
____________________________________________________________
____________________________________________________________
     I see when it begins, but not when it ends.
     Specify an end time using /to.
____________________________________________________________
____________________________________________________________
     I see when it begins, but its end remains unknown.
     Tell me when it ends.
____________________________________________________________
____________________________________________________________
     I know when, but not what.
     Give the event a description.
____________________________________________________________
____________________________________________________________
     I see when it ends, but not when it begins.
     Tell me when it begins.
____________________________________________________________
____________________________________________________________
     Your message has been observed.
     Its meaning, however, remains unknown.
____________________________________________________________
____________________________________________________________
     Current tasks under observation:
     1.[D][ ] return book (by: Sunday)
____________________________________________________________
____________________________________________________________
     Our conversation ends here.
     Until we meet again.
____________________________________________________________
```
