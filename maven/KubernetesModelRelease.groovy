def isRelease = ""
try {
  isRelease = IS_RELEASEBUILD
} catch (Throwable e) {
  isRelease = "${env.IS_RELEASEBUILD ?: 'false'}"
}
def releaseVersion = ""
try {
  releaseVersion = RELEASE_VERSION
} catch (Throwable e) {
  releaseVersion = "${env.RELEASE_VERSION}"
}
def nextSnapshotVersion = ""
try {
  nextSnapshotVersion = NEXT_SNAPSHOT_VERSION
} catch (Throwable e) {
  nextSnapshotVersion = "${env.NEXT_SNAPSHOT_VERSION}"
}

def project = 'fabric8-model'
stage 'canary release ' + project
node {
  ws (project){
    withEnv(["PATH+MAVEN=${tool 'maven-3.3.1'}/bin"]) {

      def flow = new io.fabric8.Release()

      flow.setupWorkspace 'fabric8io/' + project

      flow.release releaseVersion nextSnapshotVersion "release" isRelease
    }
  }
}
