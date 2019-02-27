# Demo Microservice

Experimentation for creating a Spring Boot WS to be deployed to Kubernetes.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

A Kubernetes cluster is needed to play with Kubernetes, although
a plain Docker installation is sufficient to experiment with running it.
Of course, running it standalone (e.g. for easier testing) is also possible.

A simple way to test locally, is to install Minikube, a standalone
K8s cluster that can run on VirtualBox. It can run on Hyper-V, as well,
but I used VirtualBox because I already had other VMs, there.

This documentation supposes that you're using IntelliJ IDEA, although
everything described is doable from the command line, as well.

You'll need the Docker command-line tools, for that, and you'll
need to run:

``` @FOR /f "tokens=*" %i IN ('minikube docker-env') DO @%i ```

to enable the ``docker`` command to connect to Docker inside Minikube.

Having Minikube running is essential, because we'll configure
IntelliJ IDEA to connect to the Docker server inside it.
Running Minikube is easier if one renames it to minikube.exe

``` minikube start --vm-driver=virtualbox ```

To connect IDEA to Docker, go to ``Settings | Build, Execution, Deployment | Docker``,
and create a new configuration, named 'Docker-Minikube'. This will leave the default
'Docker' configuration on the default Docker server, allowing you to run one locally, in the future.

Run: `minikube docker-env` and notice ``DOCKER_HOST`` and ``DOCKER_CERT_PATH``.
Use the former as a value for 'Engine API URL' (replace 'tcp' by 'https')
and the latter as the value for 'Certificates Folder', in the 'TCP Socket' option.

### Installing

First, you'll need to ``mvn package`` the project.

TODO Run/Debug Configuration

Then, on the Dockerfile, you click on the green stacked arrow symbol
and select "Build image on 'Docker-Minikube'".
That's all that is needed to build the docker image. Open the 'Docker' panel
in IntelliJ IDEA, and 'Connect' to 'Docker-Minikube'. Your image should be visible.


## Running the tests

Maven runs tests, on its own, unless you skip them.

## Deployment

```
kubectl run demo-microsvc --image=demo-microsvc:latest --port=8081
kubectl expose deployment pod/demo-microsvc --type=LoadBalancer
```
## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
* [Spring](https://spring.io/) - Framework used to create the app
* [SpringFox](https://springfox.github.io/springfox/docs/current/) - Used to generate Swagger Info

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Authors

* **Dimitrios Souflis** - *Initial work* 

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* [Baeldung](https://www.baeldung.com) seems to have examples for
anything related to Spring

