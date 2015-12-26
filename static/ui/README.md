# 
Front-end part is clone of 
[React-Bootstrap Documentation Website](https://github.com/react-bootstrap/react-bootstrap/tree/master/docs)


## Development

To run `npm run dev-run` and navigate your browser to
`http://localhost:4000`. This will start an express base node server with
webpack-dev middleware that will watch your file changes and recompile all the
static assets needed to generate the site. In the console output you'll see that
we bind to two ports. The first port is the one you'll use to load the docs in
your browser. The second is the webpack-dev-server we use to build the client
side assets in watch mode. _Note: while the docs should start on port 4000 if
that port is in use we progressively look for an available port.  Observe
console output for the actual port that we use.