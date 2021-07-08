gradle= sh gradlew clean

clean:
	@ $(gradle)

refresh:
	@ sh gradlew --refresh-dependencies

jar:
	@ $(gradle) build

test:
	@ $(gradle) test

it:
	@ $(gradle) test -Dtest.profile=integration

e2e:
	@ $(gradle) test -Dtest.profile=e2e

coverage:
	@ $(gradle) test jacocoTestReport

run:
	@ $(gradle) bootRun

project-version:
	@ grep -w "version =" build.gradle | cut -d'=' -f2 | xargs

check-vulnerability:
	@ $(gradle) check -Pstrict-security