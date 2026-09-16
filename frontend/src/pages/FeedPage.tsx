import { useEffect, useState } from "react";
import { createPost, getFeed, toggleLike, type Post } from "../api/social";
import { ApiError } from "../api/client";

export function FeedPage() {
  const [posts, setPosts] = useState<Post[]>([]);
  const [imageUrl, setImageUrl] = useState("");
  const [caption, setCaption] = useState("");
  const [error, setError] = useState<string | null>(null);
  const [posting, setPosting] = useState(false);

  async function loadFeed() {
    try {
      setPosts(await getFeed());
    } catch (err) {
      setError(err instanceof ApiError ? err.message : "Erro ao carregar o feed.");
    }
  }

  useEffect(() => {
    // eslint-disable-next-line react-hooks/set-state-in-effect -- carregar o feed ao montar a página
    loadFeed();
  }, []);

  async function handlePost(e: React.FormEvent) {
    e.preventDefault();
    if (!imageUrl.trim()) return;
    setPosting(true);
    try {
      await createPost(imageUrl, caption);
      setImageUrl("");
      setCaption("");
      await loadFeed();
    } catch (err) {
      setError(err instanceof ApiError ? err.message : "Erro ao publicar.");
    } finally {
      setPosting(false);
    }
  }

  async function handleLike(postId: number) {
    await toggleLike(postId);
    await loadFeed();
  }

  return (
    <div>
      <div className="page-head">
        <h1>Feed</h1>
      </div>
      {error && <p className="error-text">{error}</p>}

      <form onSubmit={handlePost} className="card section">
        <div className="field">
          <label htmlFor="imageUrl">URL da foto</label>
          <input id="imageUrl" value={imageUrl} onChange={(e) => setImageUrl(e.target.value)} placeholder="https://..." required />
        </div>
        <div className="field">
          <label htmlFor="caption">Legenda</label>
          <input id="caption" value={caption} onChange={(e) => setCaption(e.target.value)} placeholder="O que você quer contar?" />
        </div>
        <button type="submit" disabled={posting}>{posting ? "Publicando..." : "Publicar"}</button>
      </form>

      {posts.length === 0 && <p className="empty-state">Ninguém postou nada ainda.</p>}

      {posts.map((post) => (
        <div key={post.id} className="card post-card">
          <span className="post-author">@{post.authorHandle}</span>
          <img src={post.imageUrl} alt={post.caption ?? ""} />
          {post.caption && <p style={{ marginBottom: 8 }}>{post.caption}</p>}
          <div className="post-actions">
            <button className="ghost" style={{ padding: "4px 8px" }} onClick={() => handleLike(post.id)}>
              {post.likedByMe ? "♥ Curtido" : "♡ Curtir"} · {post.likeCount}
            </button>
            <span>{post.commentCount} comentário(s)</span>
          </div>
        </div>
      ))}
    </div>
  );
}
