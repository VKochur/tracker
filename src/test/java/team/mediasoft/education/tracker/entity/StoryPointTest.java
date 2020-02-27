package team.mediasoft.education.tracker.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StoryPointTest {

    @Test
    void updatePackage() {
        StoryPoint storyPoint1 = new StoryPoint();
        StoryPoint storyPoint2 = new StoryPoint();
        Pack pack = new Pack();
        assertNull(storyPoint1.getPack());
        assertTrue(storyPoint1.updatePackage(pack));
        assertTrue(storyPoint2.updatePackage(pack));
        assertFalse(storyPoint2.updatePackage(pack));
        assertEquals(2, pack.getStoryPoints().size());
        Pack otherPack = new Pack();
        assertTrue(storyPoint1.updatePackage(otherPack));
        assertEquals(1, pack.getStoryPoints().size());
        assertTrue(pack.getStoryPoints().contains(storyPoint2));
        assertEquals(otherPack, storyPoint1.getPack());
        assertTrue(otherPack.getStoryPoints().contains(storyPoint1));
    }

    @Test
    void removePackage() {
        Pack pack = new Pack();
        StoryPoint storyPoint1 = new StoryPoint();
        StoryPoint storyPoint2 = new StoryPoint();
        assertFalse(storyPoint1.removePackage());
        pack.addStory(storyPoint1);
        pack.addStory(storyPoint2);
        assertEquals(2, pack.getStoryPoints().size());
        assertTrue(storyPoint1.removePackage());
        assertEquals(1, pack.getStoryPoints().size());
        assertFalse(storyPoint1.removePackage());
        assertTrue(storyPoint2.removePackage());
        assertNull(storyPoint2.getPack());
        assertTrue(pack.getStoryPoints().isEmpty());
    }
}