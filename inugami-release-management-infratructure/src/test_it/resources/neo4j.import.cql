//======================================================================================================================
// CREATE INDEXES
//======================================================================================================================
CREATE INDEX idx_Artifact FOR (n:Artifact) ON (n.name,n.groupId,n.artifactId,n.packaging,n.shortName,n.version);
CREATE INDEX idx_Version FOR (n:Version) ON (n.name, n.uid,n.artifactId,n.groupId,n.major,n.minor,n.packaging,n.patch,n.tag,n.version);
CREATE INDEX idx_ErrorCode FOR (n:ErrorCode) ON (n.exploitationError,n.name,n.errorCode,n.message,n.shortName,n.statusCode, n.category);
CREATE INDEX idx_ErrorType FOR (n:ErrorType) ON (n.name,n.shortName);
CREATE INDEX idx_Service FOR (n:Service) ON (n.accept,n.autoStartup,n.bindings,n.containerFactory,n.contentType,n.converterWinsContentType,n.destination,n.errorHandler,n.event,n.exclusive,n.identifier,n.listenerId,n.method,n.name,n.payload,n.queue,n.queuesToDeclare,n.requestPayload,n.responsePayload,n.uid,n.uri,n.verb);
CREATE INDEX idx_ServiceType FOR (n:ServiceServiceType) ON (n.name, n.uid);
CREATE INDEX idx_Method FOR (n:Method) ON (n.name, n.uid,n.class,n.method,n.parameters,n.returnType);
CREATE INDEX idx_Property FOR (n:Property) ON (n.name, n.uid,n.mandatory,n.propertyType );
CREATE INDEX idx_INPUT_DTO FOR (n:INPUT_DTO) ON (n.name, n.uid);
CREATE INDEX idx_OUTPUT_DTO FOR (n:OUTPUT_DTO) ON (n.name, n.uid);
CREATE INDEX idx_LocalEntity FOR (n:LocalEntity) ON (n.name, n.uid,n.payload);
CREATE INDEX idx_Entity FOR (n:Entity) ON (n.name, n.uid,n.payload);
CREATE INDEX idx_Scm FOR (n:Scm) ON (n.name, n.uid,n.commit);
CREATE INDEX idx_Author FOR (n:Author) ON (n.name, n.uid,n.email);
CREATE INDEX idx_Glossary FOR (n:Glossary) ON (n.name, n.shortName,n.language,n.type);
CREATE INDEX idx_Issue FOR (n:Issue) ON (n.name, n.labels,n.shortName,n.title,n.url);
CREATE INDEX idx_IssueLabel FOR (n:IssueLabel) ON (n.name, n.shortName);

CREATE INDEX idx_Cve FOR (n:Cve) ON (n.uuid, n.name, n.cveSeverity, n.javaArtifact);
CREATE INDEX idx_ProductAffected FOR (n:ProductAffected) ON (n.product, n.vendor);
CREATE INDEX idx_VersionRules FOR (n:VersionRules) ON (n.major, n.minor, n.patch);


//======================================================================================================================
// INJECT DATAs
//======================================================================================================================