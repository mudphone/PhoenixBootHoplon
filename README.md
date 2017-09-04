# Hoaka

## Dependencies

  * Erlang 20.0
  * Elixir 1.5.1
  * Phoenix 1.3.0
  * Java (JVM)
  * Boot (aka boot-clj)

## What was done

  * mix phx.new web --no-brunch --no-ecto
  * update `mix.exs` elixir dep to `~> 1.5`
  * boot -d boot/new new -t hoplon -n hoaka
  * mv hoaka cljs
  * move compile web sources (scss, js, vendor, etc) to assets
  * create cljs/src/js and place boot edn and phoenix js (foreign lib) files in it

## General Info

To start your Phoenix server:

  * Install dependencies with `mix deps.get`
  * Start Phoenix endpoint with `mix phx.server`

Now you can visit [`localhost:4000`](http://localhost:4000) from your browser.

Ready to run in production? Please [check our deployment guides](http://www.phoenixframework.org/docs/deployment).

## Learn more

  * Official website: http://www.phoenixframework.org/
  * Guides: http://phoenixframework.org/docs/overview
  * Docs: https://hexdocs.pm/phoenix
  * Mailing list: http://groups.google.com/group/phoenix-talk
  * Source: https://github.com/phoenixframework/phoenix
