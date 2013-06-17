Status: [![Build Status](https://travis-ci.org/gary-rowe/BitcoinjEnforcerRules.png?branch=master)](https://travis-ci.org/gary-rowe/BitcoinjEnforcerRules)

### Bitcoinj Enforcer Rules

[Bitcoinj](https://code.google.com/p/bitcoinj/) is the first open source library for Java that allows you to spend money
without involving a third party using the [Bitcoin protocol](http://bitcoin.org).

This means that if the Bitcoinj JAR was corrupted, or made use of a corrupted library, then there is a high probability
that your Bitcoin private keys would be stolen and used to spend all funds that they controlled.

### How a dependency-chain attack works

Imagine that [SLF4J](http://www.slf4j.org/) (the popular logging framework) was hacked because it was known that Bitcoinj
(or your software) uses it. As part of the hack some code was introduced which was designed to reflectively search for objects used by Bitcoinj
on its classpath as part of its private key handling code. Since the hack is right at the source it can be assumed that
the signing key for Maven Central is compromised.

How would you detect that in your Maven build?

You may think that the SHA1 signature would protect you, but in the event of a successful attack against
Maven Central (or a mirror) they would match the digest of the downloaded artifact. In the absence of a controlled whitelist of
permitted libraries there is little that can be done within the Maven environment to protect yourself against this kind
of dependency-chain attack vector.

As an aside, using git is a very effective way to ensure that your code has not been corrupted en-route. The fingerprint
of the current commit is calculated based on the hashes of all previous history so that retroactive forgery is not
feasible.

### Why only SHA1?

It should be noted that this plugin only uses the SHA1 algorithm for digest checking. [MD5 has been cryptographically
broken](http://en.wikipedia.org/wiki/MD5) for a long time so is considered unsuitable for new projects. For the record,
SHA1 is also broken, but not to the same degree and is the only alternative supported within the Maven system. This helps
reduce the chance that an expert could manipulate the contents of the JAR to yield the same digest value whilst still
containing the malicious code.

### A local whitelist

The Bitcoinj Enforcer Rules work with the [Maven Enforcer Plugin](http://maven.apache.org/enforcer/maven-enforcer-plugin/)
to provide such a whitelist. You can choose how detailed you want it to be depending on your own security requirements,
but bear in mind that every unchecked dependency is a way in for an attacker through the back door.

### Example

The configuration below shows how you would use the Bitcoinj Enforcer Rules in your projects. Of course the values used
below are only examples.

In production you would get the list of URNs from the Bitcoinj project page over HTTPS or via the Bitcoinj git repository
when including it into your project.

```xml
<build>
  <plugins>
    ...
      <!-- Use the Bitcoinj Enforcer Rules to verify build integrity -->
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-enforcer-plugin</artifactId>
        <version>1.2</version>
        <executions>
          <execution>
            <id>enforce</id>
            <phase>verify</phase>
            <goals>
              <goal>enforce</goal>
            </goals>
            <configuration>
              <rules>
                <digestRule implementation="uk.co.froot.bitcoinj.enforcer.DigestRule">

                  <!-- Create a snapshot  -->
                  <buildSnapshot>true</buildSnapshot>

                  <!-- List of required hashes -->
                  <!-- Format is URN of groupId:artifactId:version:type:classifier:scope:hash -->
                  <!-- classifier is "null" if not present -->
                  <urns>
                    <urn>antlr:antlr:2.7.7:jar:null:compile:83cd2cd674a217ade95a4bb83a8a14f351f48bd0</urn>
                    <urn>dom4j:dom4j:1.6.1:jar:null:compile:5d3ccc056b6f056dbf0dddfdf43894b9065a8f94</urn>
                    <urn>org.bouncycastle:bcprov-jdk15:1.46:jar:null:compile:d726ceb2dcc711ef066cc639c12d856128ea1ef1</urn>
                    <urn>org.hibernate.common:hibernate-commons-annotations:4.0.1.Final:jar:null:compile:78bcf608d997d0529be2f4f781fdc89e801c9e88</urn>
                    <urn>org.hibernate.javax.persistence:hibernate-jpa-2.0-api:1.0.1.Final:jar:null:compile:3306a165afa81938fc3d8a0948e891de9f6b192b</urn>
                    <urn>org.hibernate:hibernate-core:4.1.8.Final:jar:null:compile:82b420eaf9f34f94ed5295454b068e62a9a58320</urn>
                    <urn>org.hibernate:hibernate-entitymanager:4.1.8.Final:jar:null:compile:70a29cc959862b975647f9a03145274afb15fc3a</urn>
                    <urn>org.javassist:javassist:3.15.0-GA:jar:null:compile:79907309ca4bb4e5e51d4086cc4179b2611358d7</urn>
                    <urn>org.jboss.logging:jboss-logging:3.1.0.GA:jar:null:compile:c71f2856e7b60efe485db39b37a31811e6c84365</urn>
                    <urn>org.jboss.spec.javax.transaction:jboss-transaction-api_1.1_spec:1.0.0.Final:jar:null:compile:2ab6236535e085d86f37fd97ddfdd35c88c1a419</urn>
                  </urns>

                </digestRule>
              </rules>
            </configuration>
          </execution>
        </executions>

        <!-- Ensure we download the enforcer rules -->
        <dependencies>
          <dependency>
            <groupId>uk.co.froot.bitcoinj.enforcer</groupId>
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

