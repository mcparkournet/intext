repositories {
	maven("https://papermc.io/repo/repository/maven-public") {
		content {
			includeGroup("com.destroystokyo.paper")
			includeGroup("net.md-5")
		}
	}
}

dependencies {
	api(project(":intext-core"))
	compileOnly("com.destroystokyo.paper:paper-api:1.15.2-R0.1-SNAPSHOT")
	implementation("net.kyori:text-serializer-gson:3.0.3")
}
