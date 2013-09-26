# dataweek

Disqus' Dataweek Lab

## Usage

```bash
# Install Leiningen
mkdir ~/bin/
curl -L "https://raw.github.com/technomancy/leiningen/stable/bin/lein" > ~/bin/lein
chmod 755 ~/bin/lein

# Build the project
lein do clean, deps, build

# Run the project
lein run -m disqus.dataweek.CountTopology
```

## License

Copyright © 2013 Disqus Inc.

Distributed under the MIT License.
