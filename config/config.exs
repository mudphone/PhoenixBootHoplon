# This file is responsible for configuring your application
# and its dependencies with the aid of the Mix.Config module.
#
# This configuration file is loaded before any dependency and
# is restricted to this project.
use Mix.Config

# Configures the endpoint
config :hoaka, HoakaWeb.Endpoint,
  url: [host: "localhost"],
  secret_key_base: "F3bZY3zN+99gVbW76z0xrGcHQAS8iRgd16Q3s3MjRnz3FS7JFl9KgkGZTiEScHhD",
  render_errors: [view: HoakaWeb.ErrorView, accepts: ~w(html json)],
  pubsub: [name: Hoaka.PubSub,
           adapter: Phoenix.PubSub.PG2]

# Configures Elixir's Logger
config :logger, :console,
  format: "$time $metadata[$level] $message\n",
  metadata: [:request_id]

# Import environment specific config. This must remain at the bottom
# of this file so it overrides the configuration defined above.
import_config "#{Mix.env}.exs"
