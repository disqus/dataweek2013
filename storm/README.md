# Dataweek

The Disqus Dataweek Lab Repo

## Usage

```bash
# Install Leiningen
mkdir ~/bin/
curl -L "https://raw.github.com/technomancy/leiningen/stable/bin/lein" > ~/bin/lein
chmod 755 ~/bin/lein
export PATH=$PATH:~/bin

# Clone the repo, you'll need git installed or use one of our USB sticks
git clone https://github.com/disqus/dataweek2013.git
cd dataweek2013/storm

# Build the project, you'll need java installed
lein do clean, deps, scalac

# Run the project
lein run -m disqus.dataweek.DemoTopology
```

## License

Copyright © 2013 Disqus Inc.

Distributed under the MIT License.
