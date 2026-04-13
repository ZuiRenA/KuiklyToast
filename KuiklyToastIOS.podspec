Pod::Spec.new do |s|
  s.name         = 'KuiklyToastIOS'
  s.version      = '1.0.0'
  s.summary      = 'KuiklyToast iOS Module'
  s.homepage     = 'https://github.com/Kuikly-contrib/KuiklyToast'
  s.license      = { :type => 'MIT' }
  s.author       = { 'author' => '' }
  s.source       = { :git => '', :tag => s.version.to_s }
  s.ios.deployment_target = '14.1'
  s.source_files = 'KuiklyToastIOS/**/*.{h,m}'
  s.dependency 'OpenKuiklyIOSRender', '~> 2.7.0'
end
