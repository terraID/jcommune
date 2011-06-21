/**
 * Copyright (C) 2011  jtalks.org Team
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 * Also add information on how to contact you by electronic and paper mail.
 * Creation date: Apr 12, 2011 / 8:05:19 PM
 * The jtalks.org Project
 */
package org.jtalks.jcommune.service.transactional;

import org.joda.time.DateTime;
import org.jtalks.jcommune.model.dao.PostDao;
import org.jtalks.jcommune.model.entity.Post;
import org.jtalks.jcommune.model.entity.Topic;
import org.jtalks.jcommune.model.entity.User;
import org.jtalks.jcommune.service.PostService;
import org.mockito.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * This test cover {@code TransactionalPostService} logic validation.
 * Logic validation cover update/get/error cases by this class.
 *
 * @author Osadchuck Eugeny
 * @author Kirill Afonin
 */
public class TransactionalPostServiceTest {

    final long POST_ID = 999;
    final String POST_UUID = "z1f2";
    final String POST_CONTENT = "post content";
    final DateTime POST_CREATION_DATE = new DateTime();

    final long USER_ID = 333;
    final String USER_UUID = "aaba";

    final String TOPIC_UUID = "adfadsfd";

    private PostService postService;
    private PostDao postDao;

    @BeforeMethod
    public void setUp() throws Exception {
        postDao = mock(PostDao.class);
        postService = new TransactionalPostService(postDao);
    }

    private Post getPost() {
        User topicStarter = new User();
        topicStarter.setId(USER_ID);
        topicStarter.setUuid(USER_UUID);
        topicStarter.setUsername("username");
        Topic topic = new Topic();
        topic.setId(333);
        topic.setUuid(TOPIC_UUID);
        topic.setTopicStarter(topicStarter);

        Post post = new Post();
        post.setId(POST_ID);
        post.setUuid(POST_UUID);
        post.setPostContent(POST_CONTENT);
        post.setCreationDate(POST_CREATION_DATE);
        post.setTopic(topic);
        return post;
    }

    @Test
    public void deleteByIdTest() {
        postService.delete(POST_ID);

        verify(postDao, times(1)).delete(Matchers.anyLong());
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void deleteByNagativeIdTest() {
        postService.delete(-1l);
        verify(postDao, never()).delete(Matchers.anyLong());
    }

    @Test
    public void getByIdTest() {
        when(postDao.get(POST_ID)).thenReturn(getPost());
        Post post = postService.get(POST_ID);
        Assert.assertEquals(post, getPost(), "Posts aren't equals");
        verify(postDao, times(1)).get(Matchers.anyLong());
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void getByNagativeIdTest() {
        postService.get(-1l);
        verify(postDao, never()).get(Matchers.anyLong());
    }

    @Test
    public void testGetTopicsRangeInBranch() {
        int start = 1;
        int max = 2;
        long topicId = 1L;
        List<Post> list = new ArrayList<Post>();
        list.add(Post.createNewPost());
        list.add(Post.createNewPost());
        when(postDao.getPostRangeInTopic(topicId, start, max)).thenReturn(list);

        List<Post> posts = postService.getPostRangeInTopic(topicId, start, max);

        assertNotNull(posts);
        assertEquals(max, posts.size(), "Unexpected list size");
        verify(postDao, times(1)).getPostRangeInTopic(topicId, start, max);
    }

    @Test
    public void testGetTopicsInBranchCount() {
        long topicId = 1L;
        when(postDao.getPostsInTopicCount(topicId)).thenReturn(10);

        int count = postService.getPostsInTopicCount(topicId);

        assertEquals(count, 10);
        verify(postDao, times(1)).getPostsInTopicCount(topicId);
    }

}
