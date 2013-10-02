# Dataweek

The Disqus Dataweek Lab Repo

## Usage

```bash
# Install Leiningen
mkdir ~/bin/
curl -L "https://raw.github.com/technomancy/leiningen/stable/bin/lein" > ~/bin/lein
chmod 755 ~/bin/lein

# Build the project
lein do clean, deps, scalac

# Run the project
lein run -m disqus.dataweek.DemoTopology
```

## License

Copyright © 2013 Disqus Inc.

Distributed under the MIT License.
