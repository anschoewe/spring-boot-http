services {
  name = "client"
  port = 7080
  connect {
    sidecar_service {
      proxy {
        upstreams {
          destination_name = "echo"
          local_bind_port = 9191
        }
      }
    }
  }
}
services {
  name = "echo"
  port = 8080
  connect {
    sidecar_service {}
  }
}