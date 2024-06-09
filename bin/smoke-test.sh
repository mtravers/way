# Verify that demo builds and runs
# TODO version suitable for CI
lein do clean, shadow compile app, run 1881
