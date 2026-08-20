# Sophon User Guide

Sophon is a command-line chatbot that stores tasks typed by the user, marks
or unmarks them, and lists them when requested.

## Adding Normal Tasks

Type a task and press Enter. Sophon stores it as a normal task in memory for
the current session.

Example:

```text
read book
    ____________________________________________________________
     added: read book
    ____________________________________________________________
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

## Adding Deadlines

Type `deadline`, followed by the task description, `/by`, and the deadline.
Dates and times are stored exactly as typed.

Example:

```text
deadline return book /by Sunday
    ____________________________________________________________
     Recorded. A new deadline has entered observation:
       [D][ ] return book (by: Sunday)
     1 tasks are currently under observation.
    ____________________________________________________________
```

## Adding Events

Type `event`, followed by the task description, `/from`, the start, `/to`, and
the end. Dates and times are stored exactly as typed.

Example:

```text
event project meeting /from Mon 2pm /to 4pm
    ____________________________________________________________
     Recorded. A new event has entered observation:
       [E][ ] project meeting (from: Mon 2pm to: 4pm)
     1 tasks are currently under observation.
    ____________________________________________________________
```

## Listing Tasks

Type `list` and press Enter to view the tasks added so far.

Example:

```text
list
    ____________________________________________________________
     Current tasks under observation:
     1.[ ] read book
     2.[T][ ] borrow book
     3.[D][ ] return book (by: Sunday)
     4.[E][ ] project meeting (from: Mon 2pm to: 4pm)
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

## Exiting

Type `bye` and press Enter to end the conversation.
