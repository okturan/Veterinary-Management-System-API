# Security policy

## Supported version

Security fixes target the current `master` branch. This repository is a local
API showcase rather than a hosted clinic service; historical commits and
independently deployed copies are not patched in place.

## Reporting a vulnerability

Please use [GitHub's private vulnerability reporting form](https://github.com/okturan/Veterinary-Management-System-API/security/advisories/new)
instead of opening a public issue. Include the affected endpoint, a reduced
request, clear reproduction steps, and the security impact.

Relevant reports include:

- SQL, script, markup, or OpenAPI injection through owner, animal, doctor,
  appointment, availability, vaccine, or vaccination data;
- exposure or modification of records outside the request's intended resource;
- unsafe deserialization, mass assignment, secret or connection-string
  exposure, or an unintended destructive schema action;
- a dependency, test profile, or build workflow weakness with a demonstrated
  impact.

The API does not implement authentication or tenant isolation and is not
presented as ready for internet or multi-clinic deployment. That documented
product limitation is not an authorization bypass. Scheduling-rule defects and
OpenAPI drift can be filed as normal bugs unless they cross a security boundary.

Use the synthetic in-memory walkthrough or a disposable database. Never test
with real owner, animal, appointment, or clinic records, and do not attach
credentials or destructive payloads. The maintainer will coordinate validation,
remediation, and disclosure through the private advisory.
