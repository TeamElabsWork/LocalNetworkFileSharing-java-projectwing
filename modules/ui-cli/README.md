# ui-cli (M7)

Working interactive command-line UI and the reference for how a UI talks to the system: **only** through
`UiServices` (peer directory, transfer engine, transfer store).

Commands: `help`, `peers`, `send <peer#|name> <file>`, `transfers`, `cancel <n>`, `incoming`, `quit`.
`incoming` simulates a peer sending us a file so the accept/reject flow can be tried without a second device.

Depends on: `contracts` only.
