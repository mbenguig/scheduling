package nonregressiontest.security.sessionkeyexchange;

import java.io.IOException;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import org.objectweb.proactive.ext.security.Communication;
import org.objectweb.proactive.ext.security.ProActiveSecurityManager;
import org.objectweb.proactive.ext.security.SecurityContext;
import org.objectweb.proactive.ext.security.SecurityEntity;
import org.objectweb.proactive.ext.security.crypto.AuthenticationException;
import org.objectweb.proactive.ext.security.crypto.KeyExchangeException;
import org.objectweb.proactive.ext.security.exceptions.CommunicationForbiddenException;
import org.objectweb.proactive.ext.security.exceptions.RenegotiateSessionException;
import org.objectweb.proactive.ext.security.exceptions.SecurityNotAvailableException;


public class TestSecurityEntity implements SecurityEntity {
    private ProActiveSecurityManager securityManager;

    public TestSecurityEntity(ProActiveSecurityManager securityManager) {
        this.securityManager = securityManager;
    }

    public void initiateSession(int type, SecurityEntity body)
        throws IOException, CommunicationForbiddenException, 
            AuthenticationException, RenegotiateSessionException, 
            SecurityNotAvailableException {
        securityManager.initiateSession(type, body);
    }

    public void terminateSession(long sessionID)
        throws SecurityNotAvailableException {
        securityManager.terminateSession(sessionID);
    }

    public X509Certificate getCertificate()
        throws SecurityNotAvailableException {
        return securityManager.getCertificate();
    }

    public ProActiveSecurityManager getProActiveSecurityManager() {
        return securityManager.getProActiveSecurityManager();
    }

    public long startNewSession(Communication policy)
        throws SecurityNotAvailableException, RenegotiateSessionException {
        return securityManager.startNewSession(policy);
    }

    public PublicKey getPublicKey() throws SecurityNotAvailableException {
        return securityManager.getPublicKey();
    }

    public byte[] randomValue(long sessionID, byte[] clientRandomValue)
        throws SecurityNotAvailableException, RenegotiateSessionException {
        return securityManager.randomValue(sessionID, clientRandomValue);
    }

    public byte[][] publicKeyExchange(long sessionID, byte[] myPublicKey,
        byte[] myCertificate, byte[] signature)
        throws SecurityNotAvailableException, RenegotiateSessionException, 
            KeyExchangeException {
        return securityManager.publicKeyExchange(sessionID, myPublicKey,
            myCertificate, signature);
    }

    public byte[][] secretKeyExchange(long sessionID, byte[] encodedAESKey,
        byte[] encodedIVParameters, byte[] encodedClientMacKey,
        byte[] encodedLockData, byte[] parametersSignature)
        throws SecurityNotAvailableException, RenegotiateSessionException {
        return securityManager.secretKeyExchange(sessionID, encodedAESKey,
            encodedIVParameters, encodedClientMacKey, encodedLockData,
            parametersSignature);
    }

    public SecurityContext getPolicy(SecurityContext securityContext)
        throws SecurityNotAvailableException {
        return securityManager.getPolicy(securityContext);
    }

    public String getVNName() throws SecurityNotAvailableException {
        return securityManager.getVNName();
    }

    public byte[] getCertificateEncoded() throws SecurityNotAvailableException {
        return securityManager.getCertificateEncoded();
    }

    public ArrayList getEntities() throws SecurityNotAvailableException {
        return securityManager.getEntities();
    }
}
