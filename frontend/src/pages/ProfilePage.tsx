import { useEffect, useState } from "react";
import { getMyProfile, updateProfile, type Profile } from "../api/social";
import { ApiError } from "../api/client";

export function ProfilePage() {
  const [profile, setProfile] = useState<Profile | null>(null);
  const [displayName, setDisplayName] = useState("");
  const [bio, setBio] = useState("");
  const [avatarUrl, setAvatarUrl] = useState("");
  const [error, setError] = useState<string | null>(null);
  const [saving, setSaving] = useState(false);

  useEffect(() => {
    getMyProfile()
      // eslint-disable-next-line react-hooks/set-state-in-effect -- carregar o perfil ao montar a página
      .then((p) => {
        setProfile(p);
        setDisplayName(p.displayName ?? "");
        setBio(p.bio ?? "");
        setAvatarUrl(p.avatarUrl ?? "");
      })
      .catch((err) => setError(err instanceof ApiError ? err.message : "Erro ao carregar perfil."));
  }, []);

  async function handleSave(e: React.FormEvent) {
    e.preventDefault();
    setSaving(true);
    try {
      const updated = await updateProfile({ displayName, bio, avatarUrl });
      setProfile(updated);
    } catch (err) {
      setError(err instanceof ApiError ? err.message : "Erro ao salvar perfil.");
    } finally {
      setSaving(false);
    }
  }

  if (error) return <p className="error-text">{error}</p>;
  if (!profile) return <p className="empty-state">Carregando...</p>;

  return (
    <div>
      <div className="page-head">
        <h1>Meu perfil</h1>
        <span className="pill neutral">@{profile.handle} · {profile.postCount} post(s)</span>
      </div>

      <form onSubmit={handleSave} className="card" style={{ maxWidth: 420 }}>
        <div className="field">
          <label htmlFor="displayName">Nome de exibição</label>
          <input id="displayName" value={displayName} onChange={(e) => setDisplayName(e.target.value)} />
        </div>
        <div className="field">
          <label htmlFor="bio">Bio</label>
          <textarea id="bio" rows={3} maxLength={280} value={bio} onChange={(e) => setBio(e.target.value)} placeholder="Conte um pouco sobre você" />
        </div>
        <div className="field">
          <label htmlFor="avatarUrl">URL da foto de perfil</label>
          <input id="avatarUrl" value={avatarUrl} onChange={(e) => setAvatarUrl(e.target.value)} placeholder="https://..." />
        </div>
        <button type="submit" disabled={saving}>{saving ? "Salvando..." : "Salvar"}</button>
      </form>
    </div>
  );
}
