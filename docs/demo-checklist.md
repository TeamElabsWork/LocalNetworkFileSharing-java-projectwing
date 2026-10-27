# Demo checklist

Fill in / refine in the "Demo checklist" issue. Rehearse twice before the expo.

## Before the demo
- [ ] Two devices on the same hotspot/Wi-Fi (client isolation OFF)
- [ ] App built from `main`: `mvn clean verify` passes
- [ ] Firewall allows UDP 37020 and TCP 37021 on the demo network
- [ ] A large demo file (e.g. 500 MB video) on Device A, a small file as a backup
- [ ] Download folders are empty / known

## Steps
1. [ ] Start the app on both devices - each appears in the other's peer list within a few seconds
2. [ ] Select a large file on A, choose B, click Send
3. [ ] B receives the request dialog and accepts
4. [ ] Show progress bar, speed, ETA
5. [ ] Start a second transfer to show concurrency
6. [ ] Show SHA-256 verification success
7. [ ] Cancel a transfer mid-way: both sides show CANCELLED, no `.part` file left
8. [ ] (Stretch) interrupt and resume a transfer

## Fallbacks
- Discovery blocked -> use "Add peer manually" with the IP address
- UI problem -> run the CLI (`make demo`) which has the same send/receive flow
