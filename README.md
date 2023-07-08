# inugami-release-management

Inugami release management application

## Specific Neo44 indexes

````
CREATE INDEX idx_CveImportRun FOR (n:CveImportRun) ON (n.uid, n.status);
````

## CVE scan

CVE doesn't give the real Mavan artifact gav.

- So in first step we should download all CVE to store into Neo4J
- After that we can scan all stored artifact to retrieve corresponding CVE per maven artifacts.

