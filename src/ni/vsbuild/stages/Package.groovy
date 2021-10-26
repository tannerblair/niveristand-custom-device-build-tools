package ni.vsbuild.stages

import ni.vsbuild.packages.Buildable
import ni.vsbuild.packages.PackageFactory

class Package extends AbstractStage {

   private def packages
   private boolean postBuild

   Package(script, configuration, lvVersion, postBuild = false) {
      super(script, 'Package', configuration, lvVersion)
      this.postBuild = postBuild
   }

   static boolean requiredDuringBuild(def buildConfiguration) {
      def packageInfoCollection = getPackageInfoCollection(buildConfiguration)

      for (def packageInfo : packageInfoCollection) {
         if (!packageInfo.get(multi_bitness)) {
            return true
         }
      }

      return false
   }

   static boolean requiredDuringPostBuild(def buildConfiguration) {
      def packageInfoCollection = getPackageInfoCollection(buildConfiguration)

      for (def packageInfo : packageInfoCollection) {
         if (packageInfo.get(multi_bitness)) {
            return true
         }
      }

      return false
   }

   void executeStage() {
      for (def pkg : getPackages()) {
         pkg.build()
      }
   }

   def getPackages() {
      // https://groovy-lang.org/operators.html#_direct_field_access_operator
      if (!this.@packages) {
         createPackages()
      }

      return this.@packages
   }

   private void createPackages() {
      def packageInfoCollection = getPackageInfoCollection(configuration)

      this.@packages = []
      for (def packageInfo : packageInfoCollection) {
         Buildable pkg = PackageFactory.createPackage(script, packageInfo, lvVersion)
         this.@packages.add(pkg)
      }
   }
   
   private def getPackageInfoCollection(buildConfiguration) {
      def packageInfoCollection = []
      // Developers can specify a single package [Package] or a collection of packages [[Package]].
      // Test the package information parameter and iterate as needed.
      if (buildConfiguration.packageInfo instanceof Collection) {
         packageInfoCollection = buildConfiguration.packageInfo
      }
      else {
         packageInfoCollection.add(buildConfiguration.packageInfo)
      }

      return packageInfoCollection
   }
}
