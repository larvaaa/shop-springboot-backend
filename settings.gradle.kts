plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "shopping"

include("member-service")
include("store-service")
include("order-service")
include("delivery-service")
include("admin-service")
include("apigateway-service")
include("batch-service")
include("module-common")
include("eureka-server")
include("module-common:common-core")
include("module-common:common-jpa")
include("module-common:common-security")
findProject(":module-common:common-security")?.name = "common-security"
