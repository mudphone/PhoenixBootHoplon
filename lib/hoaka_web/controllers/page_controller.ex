defmodule HoakaWeb.PageController do
  use HoakaWeb, :controller

  def index(conn, _params) do
    render conn, "index.html"
  end
end
