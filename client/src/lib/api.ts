export const apiClient = {
    get: <T>(endpoint: string): Promise<T> =>
        fetch(`/api${endpoint}`).then((res) => {
            if (!res.ok) {
                throw new Error(
                    `HTTP error at GET /api/problems! Status ${res.status}`
                );
            }
            return res.json();
        }),
    post: <T>(endpoint: string, data: unknown): Promise<T> =>
        fetch(`/api${endpoint}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        }).then((res) => {
            if (!res.ok) {
                throw new Error(`HTTP error! Status ${res.status}`);
            }
            return res.json();
        }),
};
