package ni.vsbuild.packages

class DefaultPackageStrategy implements PackageStrategy {

   def lvVersion

   DefaultPackageStrategy(lvVersion) {
      this.lvVersion = lvVersion
   }

   void filterPackageCollection(packageCollection) {
      packageCollection.removeAll { it.get('multi_bitness') }
   }
}
