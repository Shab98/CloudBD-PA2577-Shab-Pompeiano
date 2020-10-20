class init {
    exec { "aptGetUpdate":
        command => "sudo apt-get update",
        path => ["/bin", "/usr/bin"]
    }
}

class nodejs {
    package { "curl":
        ensure => latest,
        require => Exec["aptGetUpdate"]
    }
    
    exec { "curlNode":
        command => "curl -sL https://deb.nodesource.com/setup_12.x | sudo -E bash -",
        path => ["/bin", "/usr/bin"],
        require => Package["curl"]
    }
    
    package { "nodejs":
        ensure => latest,
        require => [Exec["aptGetUpdate"], Exec["curlNode"]]
    }
    
    package { "build-essential":
        ensure => latest,
        require => Exec["aptGetUpdate"]
    }
}

class mysql {
  package { "mysql-server":
        ensure => latest,
        require => Exec["aptGetUpdate"]
    }
}

node 'appserver.node' {
  include init
  include nodejs
}

node 'dbserver.node' {
  include init
  include mysql
}

node default {
  include init
}
