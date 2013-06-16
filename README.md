Status: [![Build Status](https://travis-ci.org/gary-rowe/BitcoinjEnforcerRules.png?branch=master)](https://travis-ci.org/gary-rowe/BitcoinjEnforcerRules)

## Bitcoinj Enforcer Rules

[Bitcoinj](https://code.google.com/p/bitcoinj/) is the first open source library for Java that allows you to spend money
without involving a third party.

This means that if the Bitcoinj JAR was corrupted, or made use of a corrupted library, then there is a high probability
that your Bitcoin private keys would be obtained and used to spend any and all funds that they controlled.

## How a side-chain attack works

Imagine that [SLF4J](http://www.slf4j.org/) (the popular logging framework) was hacked because it was known that Bitcoinj (or your software) uses
it. As part of the hack some code was introduced which was designed to reflectively search for objects used by Bitcoinj
on its classpath as part of its private key handling code. Since the hack is right at the source it can be assumed that
the signing key for Maven Central is compromised.

How would you detect that in your Maven build?

You may think that the SHA1 signature would protect you, but in the event of a successful attack against
Maven Central (or a mirror) they would match the digest of the downloaded artifact. In the absence of a controlled whitelist of
permitted libraries there is little that can be done within the Maven environment to protect yourself against this kind
of side-chain attack vector.

## Why only SHA1?

It should be noted that this plugin only uses the SHA1 algorithm for digest checking. [MD5 has been cryptographically
broken](http://en.wikipedia.org/wiki/MD5) for a long time so is considered unsuitable for new projects. For the record,
SHA1 is also broken, but not to the same degree and is the only alternative supported within the Maven system. This helps
reduce the chance that an expert could manipulate the contents of the JAR to yield the same digest value whilst still
containing the malicious code.

## A local whitelist

The Bitcoinj Enforcer Rules work with the [Maven Enforcer Plugin](http://maven.apache.org/enforcer/maven-enforcer-plugin/)
to provide such a whitelist. You can choose how detailed you want it to be depending on your own security requirements,
but bear in mind that every unchecked dependency is a way in for an attacker through the back door.

## Example only! Do not use this configuration in production!

This is not safe for production use. Make sure that you obtain the official version over HTTPS or through the Bitcoinj
git repository when including it into your project.

```xml
<build>
  <plugins>
    ...
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-enforcer-plugin</artifactId>
      <version>1.2</version>
      <executions>
        <execution>
          <id>enforce</id>
          <configuration>
            <rules>
              <digestRule implementation="com.google.bitcoinj.enforcer.DigestRule">
                <!-- List of required hashes -->
                <!-- Format is URN of groupId:artifactId:version:type:classifier:scope:hash -->
                <!-- classifier is "null" if not present -->
                <urns>
                    <urn>org.bouncycastle:bcprov-jdk15:1.46:jar:null:compile:d726ceb2dcc711ef066cc639c12d856128ea1ef1</urn>
                </urns>

                <!-- Set this to true to build a whitelist for your project after verification -->
                <buildSnapshot>false</buildSnapshot>
              </digestRule>
            </rules>
          </configuration>
          <phase>verify</phase>
          <goals>
            <goal>enforce</goal>
          </goals>
        </execution>
      </executions>

      <!-- Use a plugin-specific dependency set to ensure the rules are downloaded -->
      <!-- (this artifact can be added to the whitelist if required) -->
      <dependencies>
        <dependency>
          <groupId>com.google.bitcoinj</groupId>
          <artifactId>bitcoinj-enforcer-rules</artifactId>
          <version>0.0.1-SNAPSHOT</version>
        </dependency>
      </dependencies>

    </plugin>
    ...
  </plugins>
</build>

```

## How to use it

For maximum effect, the rules should be triggered during the `verify` phase so that all the dependencies that could affect
the build will have been pulled in. This has the useful side effect that as a developer you're not continuously checking
yourself for every build - only when you're about to perform an `install` or `deploy`.

You may want to grep/find on a case-sensitive match for "URN" to find the verification messages.

You can try it out on itself by building it within this reactor project:

```shell
mvn clean install
```

The reactor will first build the Bitcoinj Enforcer Rules and then go on to build another artifact that depends on them
working (the Rule Tester project). This second project demonstrates how you would include Bitcoinj Enforcer Rules in
 your projects.

## Building the whitelist automatically for large projects

Clearly trying to manually create the list of URNs would be a painful process, particularly in a large project with many
layers of transitive dependencies to explore. Fortunately, the `buildSnapshot` flag will cause the plugin to examine all
the resolved dependencies within your project and build a list of URNs that you can copy-paste (with caution) into your build.

