#!/usr/bin/groovy

def call(body) {
    // evaluate the body block, and collect configuration into the object
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    def yaml = getJavaKubernetesYaml {
        port = config.port
        label = config.label
        icon = config.icon
        version = config.version
        exposeApp = config.exposeApp
    }

    kubernetesApply(file: yaml, environment: config.environment)
    return yaml
}
