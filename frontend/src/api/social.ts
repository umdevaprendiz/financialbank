import { del, get, post, put } from "./client";

export type Profile = { handle: string; displayName: string | null; bio: string | null; avatarUrl: string | null; postCount: number };

export type Post = {
  id: number;
  authorHandle: string;
  authorAvatarUrl: string | null;
  imageUrl: string;
  caption: string | null;
  dateCreation: string;
  likeCount: number;
  likedByMe: boolean;
  commentCount: number;
};

export type Comment = { id: number; authorHandle: string; text: string; dateCreation: string };

export const getMyProfile = () => get<Profile>("/api/social/profile/me");
export const getProfileByHandle = (handle: string) => get<Profile>(`/api/social/profile/${handle}`);
export const updateProfile = (data: { displayName?: string; bio?: string; avatarUrl?: string }) =>
  put<Profile>("/api/social/profile/me", data);

export const getFeed = () => get<Post[]>("/api/social/posts/feed");
export const getPostsByHandle = (handle: string) => get<Post[]>(`/api/social/posts/by/${handle}`);
export const createPost = (imageUrl: string, caption: string) =>
  post<Post>("/api/social/posts", { imageUrl, caption });
export const deletePost = (postId: number) => del<void>(`/api/social/posts/${postId}`);
export const toggleLike = (postId: number) => post<void>(`/api/social/posts/${postId}/like`);
export const getComments = (postId: number) => get<Comment[]>(`/api/social/posts/${postId}/comments`);
export const addComment = (postId: number, text: string) =>
  post<Comment>(`/api/social/posts/${postId}/comments`, { text });
