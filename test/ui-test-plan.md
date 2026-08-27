# UI Test Plan

- Working directory: project root
- Setup command: `javac -encoding UTF-8 -d out\ui-test src\main\java\*.java`
- Comparison: exact text after normalizing line endings and trimming trailing spaces from each line

## Test Cases

### TC-01: Exit immediately

Aim: Verify that Sophon greets the user and exits cleanly when the user enters `bye`.

Command:
```text
powershell -NoProfile -Command "Remove-Item -LiteralPath 'data\sophon.txt' -ErrorAction SilentlyContinue; java '-Dfile.encoding=UTF-8' '-Dsun.stdout.encoding=UTF-8' '-Dsun.stderr.encoding=UTF-8' -cp out\ui-test Sophon"
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

### TC-10: Handle save path failure

Aim: Verify that Sophon shows a friendly message if the task list cannot be saved.

Command:
```text
powershell -NoProfile -Command "$dataPath = Join-Path (Get-Location) 'data'; if (Test-Path -LiteralPath $dataPath) { $resolvedPath = (Resolve-Path -LiteralPath $dataPath).Path; if ($resolvedPath -eq $dataPath) { Remove-Item -LiteralPath $dataPath -Recurse -Force } }; [System.IO.File]::WriteAllText($dataPath, 'not a directory'); $commands = @('todo read book', 'bye') -join [Environment]::NewLine; $commands | java '-Dfile.encoding=UTF-8' '-Dsun.stdout.encoding=UTF-8' '-Dsun.stderr.encoding=UTF-8' -cp out\ui-test Sophon; Remove-Item -LiteralPath $dataPath -Force -ErrorAction SilentlyContinue"
```

Inputs:
```text

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
     I could not save the task list.
____________________________________________________________
____________________________________________________________
     Our conversation ends here.
     Until we meet again.
____________________________________________________________
```

### TC-08: Reject file separator in task details

Aim: Verify that Sophon rejects task details containing the save-file separator.

Command:
```text
powershell -NoProfile -Command "Remove-Item -LiteralPath 'data\sophon.txt' -ErrorAction SilentlyContinue; java '-Dfile.encoding=UTF-8' '-Dsun.stdout.encoding=UTF-8' '-Dsun.stderr.encoding=UTF-8' -cp out\ui-test Sophon"
```

Inputs:
```text
todo read | book
deadline return book /by June | 6
event meeting /from 2pm | 3pm /to 4pm
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
     Please do not use " | " in task details.
____________________________________________________________
____________________________________________________________
     Please do not use " | " in task details.
____________________________________________________________
____________________________________________________________
     Please do not use " | " in task details.
____________________________________________________________
____________________________________________________________
     Current tasks under observation:
____________________________________________________________
____________________________________________________________
     Our conversation ends here.
     Until we meet again.
____________________________________________________________
```

### TC-09: Handle invalid save file

Aim: Verify that Sophon shows a friendly startup message when `data\sophon.txt` is malformed.

Command:
```text
powershell -NoProfile -Command "New-Item -ItemType Directory -Force -Path data | Out-Null; [System.IO.File]::WriteAllLines('data\sophon.txt', [string[]]@('X | 0 | mystery task'), [System.Text.UTF8Encoding]::new($false)); $commands = @('list', 'bye') -join [Environment]::NewLine; $commands | java '-Dfile.encoding=UTF-8' '-Dsun.stdout.encoding=UTF-8' '-Dsun.stderr.encoding=UTF-8' -cp out\ui-test Sophon"
```

Inputs:
```text

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
     The save file contains an unknown task type.
____________________________________________________________
____________________________________________________________
     Current tasks under observation:
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
powershell -NoProfile -Command "Remove-Item -LiteralPath 'data\sophon.txt' -ErrorAction SilentlyContinue; java '-Dfile.encoding=UTF-8' '-Dsun.stdout.encoding=UTF-8' '-Dsun.stderr.encoding=UTF-8' -cp out\ui-test Sophon"
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
powershell -NoProfile -Command "Remove-Item -LiteralPath 'data\sophon.txt' -ErrorAction SilentlyContinue; java '-Dfile.encoding=UTF-8' '-Dsun.stdout.encoding=UTF-8' '-Dsun.stderr.encoding=UTF-8' -cp out\ui-test Sophon"
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

### TC-04: Delete tasks and handle invalid delete commands

Aim: Verify that Sophon removes the requested task, keeps the remaining tasks listed correctly, and explains invalid delete commands.

Command:
```text
powershell -NoProfile -Command "Remove-Item -LiteralPath 'data\sophon.txt' -ErrorAction SilentlyContinue; java '-Dfile.encoding=UTF-8' '-Dsun.stdout.encoding=UTF-8' '-Dsun.stderr.encoding=UTF-8' -cp out\ui-test Sophon"
```

Inputs:
```text
delete
delete abc
delete 1
todo read book
todo borrow book
delete 0
delete 3
delete 1
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
     Tell me which task to remove.
____________________________________________________________
____________________________________________________________
     Task numbers must be written as numerals.
____________________________________________________________
____________________________________________________________
     No task exists at that number.
____________________________________________________________
____________________________________________________________
     Recorded. A new task has entered observation:
       [T][ ] read book
     1 tasks are currently under observation.
____________________________________________________________
____________________________________________________________
     Recorded. A new task has entered observation:
       [T][ ] borrow book
     2 tasks are currently under observation.
____________________________________________________________
____________________________________________________________
     No task exists at that number.
____________________________________________________________
____________________________________________________________
     No task exists at that number.
____________________________________________________________
____________________________________________________________
     Removed. This task is no longer under observation:
       [T][ ] read book
     1 tasks remain under observation.
____________________________________________________________
____________________________________________________________
     Current tasks under observation:
     1.[T][ ] borrow book
____________________________________________________________
____________________________________________________________
     Our conversation ends here.
     Until we meet again.
____________________________________________________________
```

### TC-05: Mark and unmark tasks with invalid inputs

Aim: Verify that Sophon marks and unmarks valid tasks, and explains invalid mark and unmark commands.

Command:
```text
powershell -NoProfile -Command "Remove-Item -LiteralPath 'data\sophon.txt' -ErrorAction SilentlyContinue; java '-Dfile.encoding=UTF-8' '-Dsun.stdout.encoding=UTF-8' '-Dsun.stderr.encoding=UTF-8' -cp out\ui-test Sophon"
```

Inputs:
```text
mark
mark abc
mark 1
unmark
unmark xyz
unmark 1
todo read book
mark 0
mark 2
mark 1
unmark 0
unmark 2
unmark 1
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
     Tell me which task has completed its observation.
____________________________________________________________
____________________________________________________________
     Task numbers must be written as numerals.
____________________________________________________________
____________________________________________________________
     No task exists at that number.
____________________________________________________________
____________________________________________________________
     Tell me which task has returned to observation.
____________________________________________________________
____________________________________________________________
     Task numbers must be written as numerals.
____________________________________________________________
____________________________________________________________
     No task exists at that number.
____________________________________________________________
____________________________________________________________
     Recorded. A new task has entered observation:
       [T][ ] read book
     1 tasks are currently under observation.
____________________________________________________________
____________________________________________________________
     No task exists at that number.
____________________________________________________________
____________________________________________________________
     No task exists at that number.
____________________________________________________________
____________________________________________________________
     Acknowledged. This task is now complete:
       [T][X] read book
____________________________________________________________
____________________________________________________________
     No task exists at that number.
____________________________________________________________
____________________________________________________________
     No task exists at that number.
____________________________________________________________
____________________________________________________________
     Reverted. This task is once again incomplete:
       [T][ ] read book
____________________________________________________________
____________________________________________________________
     Current tasks under observation:
     1.[T][ ] read book
____________________________________________________________
____________________________________________________________
     Our conversation ends here.
     Until we meet again.
____________________________________________________________
```

### TC-06: Save tasks after changes

Aim: Verify that Sophon writes the latest task list to `data\sophon.txt` after add, mark, and delete commands.

Command:
```text
powershell -NoProfile -Command "$commands = @('todo read book', 'deadline return book /by Sunday', 'event project meeting /from Mon 2pm /to 4pm', 'mark 1', 'delete 2', 'bye') -join [Environment]::NewLine; Remove-Item -LiteralPath 'data\sophon.txt' -ErrorAction SilentlyContinue; $commands | java '-Dfile.encoding=UTF-8' '-Dsun.stdout.encoding=UTF-8' '-Dsun.stderr.encoding=UTF-8' -cp out\ui-test Sophon; 'SAVED FILE:'; Get-Content -LiteralPath 'data\sophon.txt'"
```

Inputs:
```text

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
     Acknowledged. This task is now complete:
       [T][X] read book
____________________________________________________________
____________________________________________________________
     Removed. This task is no longer under observation:
       [D][ ] return book (by: Sunday)
     2 tasks remain under observation.
____________________________________________________________
____________________________________________________________
     Our conversation ends here.
     Until we meet again.
____________________________________________________________
SAVED FILE:
T | 1 | read book
E | 0 | project meeting | Mon 2pm | 4pm
```

### TC-07: Load tasks on startup

Aim: Verify that Sophon loads todos, deadlines, and events from `data\sophon.txt` when it starts.

Command:
```text
powershell -NoProfile -Command "New-Item -ItemType Directory -Force -Path data | Out-Null; [System.IO.File]::WriteAllLines('data\sophon.txt', [string[]]@('T | 1 | read book', 'D | 0 | return book | Sunday', 'E | 0 | project meeting | Mon 2pm | 4pm'), [System.Text.UTF8Encoding]::new($false)); $commands = @('list', 'bye') -join [Environment]::NewLine; $commands | java '-Dfile.encoding=UTF-8' '-Dsun.stdout.encoding=UTF-8' '-Dsun.stderr.encoding=UTF-8' -cp out\ui-test Sophon"
```

Inputs:
```text

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
     Current tasks under observation:
     1.[T][X] read book
     2.[D][ ] return book (by: Sunday)
     3.[E][ ] project meeting (from: Mon 2pm to: 4pm)
____________________________________________________________
____________________________________________________________
     Our conversation ends here.
     Until we meet again.
____________________________________________________________
```
