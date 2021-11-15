
function copyEnvVarsToGradleProperties {
    GRADLE_PROPERTIES=$HOME"/.gradle/gradle.properties"
    export GRADLE_PROPERTIES
    echo "Gradle Properties should exist at $GRADLE_PROPERTIES"

    if [ ! -f "$GRADLE_PROPERTIES" ]; then
        echo "Gradle Properties does not exist"

        echo "Creating Gradle Properties file..."
        touch "$GRADLE_PROPERTIES"

        echo "Writing Private keys to gradle.properties..."
        # shellcheck disable=SC2129
        echo "API_KEY=$ORG_GRADLE_PROJECT_API_KEY" >> "$GRADLE_PROPERTIES"

        echo "android.useAndroidX=true" >> "$GRADLE_PROPERTIES"
        echo "android.enableJetifier=true" >> "$GRADLE_PROPERTIES"

    fi
}