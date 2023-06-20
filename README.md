# BlurView

### Installation

Add this to your ```build.gradle``` file

```
repositories {
    maven {
        url "https://jitpack.io"
    }
}

dependencies {
	 implementation 'com.github.babayevsemid:BlurView:1.0.0-alpha03'
}
``` 

### Use

```
  binding.blurView.setupWith(binding.root)
    .setBlurAlgorithm(RenderScriptBlur(applicationContext))
    ?.setBlurRadius(1f)
    ?.setBlurAutoUpdate(true)
    ?.setHasFixedTransformationMatrix(true)

   binding.blurView.setOnClickListener {
      binding.blurView.setBlurEnabled(!binding.blurView.isBlurEnabled)
   }
