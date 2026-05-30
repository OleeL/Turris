.PHONY: clean package package-all run debug \
	package-windows-x64 package-windows-x86 package-macos-x64 package-macos-arm64 package-linux-x64 package-linux-arm32 package-linux-arm64

clean:
	mvn clean

package:
	mvn package

package-windows-x64:
	mvn -P windows-x64 package

package-windows-x86:
	mvn -P windows-x86 package

package-macos-x64:
	mvn -P macos-x64 package

package-macos-arm64:
	mvn -P macos-arm64 package

package-linux-x64:
	mvn -P linux-x64 package

package-linux-arm32:
	mvn -P linux-arm32 package

package-linux-arm64:
	mvn -P linux-arm64 package

package-all:
	mvn clean
	mvn -P windows-x64 package
	mvn -P windows-x86 package
	mvn -P macos-x64 package
	mvn -P macos-arm64 package
	mvn -P linux-x64 package
	mvn -P linux-arm32 package
	mvn -P linux-arm64 package

run:
	mvn package exec:java -Dexec.mainClass="com.team62.turris.Main"

debug:
	mvn package exec:java -Dexec.mainClass="com.team62.turris.Main"
