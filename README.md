Status: [![Build Status](https://travis-ci.org/gary-rowe/BitcoinEnforcerRules.png?branch=master)](https://travis-ci.org/gary-rowe/BitcoinEnforcerRules)

## Bitcoinj Enforcer Rules

[Bitcoinj](https://code.google.com/p/bitcoinj/) is the first open source library for Java that allows you to spend money
without involving a third party.

This means that if the Bitcoinj JAR was corrupted, or made use of a corrupted library, then there is a high probability
that your Bitcoin private keys would be obtained and used to spend any and all funds that they controlled.

## How a side-chain attack works

Imagine that SLF4J (the popular logging framework) was hacked because it was known that Bitcoinj (or your software) uses
it. As part of the hack some code was introduced which was designed to reflectively search for objects used by Bitcoinj
on its classpath as part of its private key handling code. Since the hack is right at the source it can be assumed that
the signing key for Maven Central is compromised.

How would you detect that in your Maven build?

You may think that the SHA1 and MD5 signatures would protect you, but in the event of a successful attack against
Maven Central (or a mirror) they would match the digest of the downloaded artifact. In the absence of a controlled white list of
permitted libraries there is little that can be done within the Maven environment to protect yourself against this kind
of side-chain attack vector.

## A local whitelist

The Bitcoin Enforcer Rules work with the [Maven Enforcer Plugin](http://maven.apache.org/enforcer/maven-enforcer-plugin/)
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
              <digestRule implementation="com.google.bitcoinj.enforcer.SHA1SignatureRule">
                <!-- List of required hashes -->
                <!-- Format is URN of groupId:artifactId:version:type:classifier:scope:algorithm:hash -->
                <!-- classifier is "null" if not present -->
                <!-- algorithm is "sha1" or "md5" with "sha1" preferred -->
                <urns>
                  <urn>com.google:bitcoinj:0.5.0:jar:null:compile:sha1:923164f40d38caa012ca08861092dd1d5ee6f4b9</urn>
                  <urn>org.bouncycastle:bcprov-jdk15:1.46:jar:null:compile:md5:d726ceb2dcc711ef066cc639c12d856128ea1ef1</urn>
                </urns>
              </digestRule>
            </rules>
          </configuration>
          <goals>
            <goal>enforce</goal>
          </goals>
        </execution>
      </executions>

      <!-- Ensure the rules are downloaded (we can include them in the URN whitelist) -->
      <dependencies>
        <dependency>
          <groupId>com.google.bitcoinj</groupId>
          <artifactId>bitcoinj-com.google.bitcoinj.enforcer-rules</artifactId>
          <version>0.0.1-SNAPSHOT</version>
        </dependency>
      </dependencies>

    </plugin>
    ...
  </plugins>
</build>

```
