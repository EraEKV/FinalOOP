package Research;

import Research.ResearchJournalsName;
import Research.ResearchPaper;
import Research.Subscriber;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a research journal that contains a set of research papers and subscribers.
 * The journal can notify its subscribers about updates.
 */
public class ResearchJournal implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * The name of the research journal.
     */
    private ResearchJournalsName researchJournalsName;

    /**
     * A set of subscribers to the research journal.
     */
    private final Set<Subscriber> subscribers = new HashSet<>();

    /**
     * A set of research papers included in the journal.
     */
    private final Set<ResearchPaper> researchPapers = new HashSet<>();

    public ResearchJournal() {}

    public ResearchJournal(ResearchJournalsName researchJournalsName) {
        this.researchJournalsName = researchJournalsName;
    }

    public ResearchJournalsName getResearchJournalsName() {
        return researchJournalsName;
    }

    public void setResearchJournalsName(ResearchJournalsName name) {
        this.researchJournalsName = name;
    }

    public Set<Subscriber> getSubscribers() {
        return subscribers;
    }

    public Set<ResearchPaper> getResearchPapers() {
        return researchPapers;
    }

    /**
     * Subscribes a new subscriber to the journal.
     *
     * @param subscriber the subscriber to add
     */
    public void subscribe(Subscriber subscriber) {
        if (subscriber != null) {
            subscribers.add(subscriber);
        }
    }

    /**
     * Unsubscribes an existing subscriber from the journal.
     *
     * @param subscriber the subscriber to remove
     */
    public void unsubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    /**
     * Notifies all subscribers about an update.
     * This calls the update method on each subscriber.
     */
    public void notifySubscribers() {
        subscribers.forEach(Subscriber::update);
    }

    /**
     * Checks if this ResearchJournal is equal to another object.
     * Two ResearchJournal instances are considered equal if they have the same name.
     *
     * @param o the object to compare
     * @return {@code true} if the objects are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResearchJournal that = (ResearchJournal) o;
        return Objects.equals(researchJournalsName, that.researchJournalsName);
    }

    /**
     * Returns the hash code for this ResearchJournal.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(researchJournalsName);
    }

    /**
     * Returns a string representation of this ResearchJournal.
     *
     * @return a string containing the journal name, number of subscribers, and number of research papers
     */
    @Override
    public String toString() {
        return "ResearchJournal{" +
                "researchJournalsName=" + researchJournalsName +
                ", subscribers=" + subscribers.size() +
                ", researchPapers=" + researchPapers.size() +
                '}';
    }
}
