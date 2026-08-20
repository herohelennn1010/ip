---
name: test-ui
description: Run console UI regression tests for this Java iP project from test/ui-test-plan.md, comparing each command's output with the expected output and stopping on the first failure.
metadata:
  short-description: Run console UI tests
---

# Test UI

Use this project-specific skill when the user asks to run, create, update, or report console UI tests for the chatbot.

The source of truth is `test/ui-test-plan.md`. Keep the test plan in that file and make each test case state:

- the aim of the test case
- the command that launches the program
- the console input to provide to that command
- the expected console output

Use `scripts/run_ui_tests.py` to execute the plan. It reads the plan, runs any setup command, runs test cases in order, shows a transcript of the console input and output, and exits immediately on the first failed test. On failure, report the failing test case, actual output, and expected output.

The runner compares output after normalizing line endings and trimming trailing whitespace from each line. Do not weaken the comparison further unless the user explicitly chooses a looser test style.

Typical use from the project root:

```bash
python .codex/skills/test-ui/scripts/run_ui_tests.py
```

If `python` is not available on Windows, use `py` with the same script path.
