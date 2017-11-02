package ni.vsbuild.v2.steps

import ni.vsbuild.v2.BuildConfiguration

class LvBuildSpecAllTargetsStep extends LvBuildStep {

   def spec
   
   LvBuildSpecAllTargetsStep(script, jsonStep) {
      super(script, jsonStep)
      this.spec = jsonStep.getString('build_spec')
   }
   
   void executeStep(BuildConfiguration configuration) {
      script.lvBuildSpecAllTargets(resolveProject(configuration), spec, lvVersion)
   }
}
