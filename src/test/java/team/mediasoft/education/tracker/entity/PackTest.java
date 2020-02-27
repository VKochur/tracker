package team.mediasoft.education.tracker.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PackTest {

    @Test
    void addStory() {
        Pack pack = new Pack();
        StoryPoint storyPoint1 = new StoryPoint();
        StoryPoint storyPoint2 = new StoryPoint();
        assertFalse(pack.removeStory(storyPoint1));
        assertTrue(pack.addStory(storyPoint1));
        assertFalse(pack.addStory(storyPoint1));
        assertEquals(1, pack.getStoryPoints().size());

        assertNull(storyPoint2.getPack());
        assertTrue(pack.addStory(storyPoint2));
        assertEquals(2, pack.getStoryPoints().size());

        assertEquals(pack, storyPoint1.getPack());
        assertEquals(pack, storyPoint2.getPack());

        Pack otherPack = new Pack();
        otherPack.addStory(storyPoint1);
        assertEquals(1, pack.getStoryPoints().size());
        assertTrue(pack.getStoryPoints().contains(storyPoint2));
        assertTrue(otherPack.getStoryPoints().contains(storyPoint1));
        assertEquals(otherPack, storyPoint1.getPack());
    }

    @Test
    void removeStory() {
        Pack pack = new Pack();
        StoryPoint storyPoint1 = new StoryPoint();
        StoryPoint storyPoint2 = new StoryPoint();
        assertFalse(pack.removeStory(storyPoint1));
        pack.addStory(storyPoint1);
        pack.addStory(storyPoint2);
        assertEquals(2, pack.getStoryPoints().size());
        assertEquals(pack, storyPoint1.getPack());
        assertTrue(pack.removeStory(storyPoint1));
        assertFalse(pack.getStoryPoints().contains(storyPoint1));
        assertNull(storyPoint1.getPack());
        assertEquals(1, pack.getStoryPoints().size());
        assertTrue(pack.removeStory(storyPoint2));
        assertNull(storyPoint2.getPack());
        assertTrue(pack.getStoryPoints().isEmpty());
    }
}