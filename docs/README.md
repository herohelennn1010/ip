# Sophon User Guide

Sophon is a command-line chatbot that helps you track todos, deadlines, and
events. It saves your task list automatically, so your tasks are still there
the next time you start the chatbot from the same project folder.

## Quick Start

Run Sophon, type one command at a time, and press Enter after each command.

Common commands:

```text
todo read book
deadline return book /by 2019-10-15
event project meeting /from 2019-10-15 /to 2019-10-16
list
mark 1
unmark 1
delete 1
bye
```

## Adding ToDos

Type `todo`, followed by the task description.

Example:

```text
todo borrow book
    ____________________________________________________________
     Recorded. A new task has entered observation:
       [T][ ] borrow book
     1 tasks are currently under observation.
    ____________________________________________________________
```

Sophon rejects a todo if the description is missing.

## Adding Deadlines

Type `deadline`, followed by the task description, `/by`, and the deadline
date in `yyyy-MM-dd` format.

Example:

```text
deadline return book /by 2019-10-15
    ____________________________________________________________
     Recorded. A new deadline has entered observation:
       [D][ ] return book (by: Oct 15 2019)
     1 tasks are currently under observation.
    ____________________________________________________________
```

Sophon rejects a deadline if the description, `/by`, or deadline date is
missing or not in `yyyy-MM-dd` format.

## Adding Events

Type `event`, followed by the task description, `/from`, the start, `/to`, and
the end. Start and end dates must be in `yyyy-MM-dd` format.

Example:

```text
event project meeting /from 2019-10-15 /to 2019-10-16
    ____________________________________________________________
     Recorded. A new event has entered observation:
       [E][ ] project meeting (from: Oct 15 2019 to: Oct 16 2019)
     1 tasks are currently under observation.
    ____________________________________________________________
```

Sophon rejects an event if the description, start date, or end date is missing
or not in `yyyy-MM-dd` format.

## Listing Tasks

Type `list` and press Enter to view the tasks added so far.

Example:

```text
list
    ____________________________________________________________
     Current tasks under observation:
     1.[T][ ] borrow book
     2.[D][ ] return book (by: Oct 15 2019)
     3.[E][ ] project meeting (from: Oct 15 2019 to: Oct 16 2019)
    ____________________________________________________________
```

## Marking Tasks

Type `mark` followed by a task number to mark that task as done. Type `unmark`
followed by a task number to mark it as not done.

Example:

```text
mark 2
    ____________________________________________________________
     Acknowledged. This task is now complete:
       [T][X] borrow book
    ____________________________________________________________
```

Example:

```text
unmark 2
    ____________________________________________________________
     Reverted. This task is once again incomplete:
       [T][ ] borrow book
    ____________________________________________________________
```

## Deleting Tasks

Type `delete` followed by a task number to remove that task.

Example:

```text
delete 2
     ____________________________________________________________
     Removed. This task is no longer under observation:
       [D][ ] return book (by: Oct 15 2019)
     2 tasks remain under observation.
    ____________________________________________________________
```

## Saving and Loading

Sophon saves the task list automatically after you add, mark, unmark, or delete
a task. The save file is stored at `data/sophon.txt` relative to the project
folder.

If the save file or `data` folder does not exist yet, Sophon starts normally.
The folder and file are created automatically the first time Sophon saves your
tasks.

Do not type ` | ` inside task details, because Sophon uses that separator in
the save file.

## Handling Mistakes

If Sophon cannot understand a command or a command is missing required details,
it shows an explanation instead of crashing.

Examples:

```text
todo
    ____________________________________________________________
     You have given me nothing to observe.
     A todo requires a description.
    ____________________________________________________________
```

```text
mark abc
    ____________________________________________________________
     Task numbers must be written as numerals.
    ____________________________________________________________
```

## Exiting

Type `bye` and press Enter to end the conversation.
