./gradlew -PpluginPublish publishPluginPublicationToMavenLocal
./gradlew -PcorePublish :widgets:publishToMavenLocal
(cd sample/ios-app && pod install)
./gradlew -PadditionsPublish :widgets-flat:publishToMavenLocal :widgets-bottomsheet:publishToMavenLocal :widgets-sms:publishToMavenLocal :widgets-datetime-picker:publishToMavenLocal :widgets-collection:publishToMavenLocal