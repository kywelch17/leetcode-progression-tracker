DROP TABLE IF EXISTS attempts;
DROP TABLE IF EXISTS problems;

CREATE TABLE problems (
    problem_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    difficulty VARCHAR(20) NOT NULL,
    note VARCHAR(1500),
    title VARCHAR(255) NOT NULL,
    url VARCHAR(500),
    created_at TIMESTAMP
);

CREATE TABLE attempts (
    attempt_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    note VARCHAR(1500),
    is_successful BOOLEAN,
    attempted_at TIMESTAMP,
    problem_id BIGINT,
    CONSTRAINT fk_attempts_problems FOREIGN KEY (problem_id) REFERENCES problems(problem_id)
);

INSERT INTO problems (difficulty, note, title, url, created_at) VALUES
	('EASY', 'Use a hash map to store each value''s index while scanning the array once.', 'Two Sum', 'https://leetcode.com/problems/two-sum/', '2026-08-01 09:00:00'),
	('EASY', 'Maintain a slow pointer for the position of the next unique value.', 'Remove Duplicates from Sorted Array', 'https://leetcode.com/problems/remove-duplicates-from-sorted-array/', '2026-08-02 09:00:00'),
	('MEDIUM', 'Expand around every possible center and track the longest odd and even palindrome.', 'Longest Palindromic Substring', 'https://leetcode.com/problems/longest-palindromic-substring/', '2026-08-03 09:00:00'),
	('MEDIUM', 'Sort intervals by start time, then merge whenever the next interval overlaps the current one.', 'Merge Intervals', 'https://leetcode.com/problems/merge-intervals/', '2026-08-04 09:00:00'),
	('HARD', 'Use a monotonic stack to find the next greater temperature for each day.', 'Daily Temperatures', 'https://leetcode.com/problems/daily-temperatures/', '2026-08-05 09:00:00'),
	('HARD', 'Binary search the smaller array partition so the left and right halves have balanced ordering.', 'Median of Two Sorted Arrays', 'https://leetcode.com/problems/median-of-two-sorted-arrays/', '2026-08-06 09:00:00');

INSERT INTO attempts (note, is_successful, attempted_at, problem_id) VALUES
	('First pass used nested loops and worked, but it was O(n^2).', TRUE, '2026-08-01 09:30:00', (SELECT problem_id FROM problems WHERE title = 'Two Sum')),
	('Reworked the solution with a hash map and reached O(n) time.', TRUE, '2026-08-01 10:15:00', (SELECT problem_id FROM problems WHERE title = 'Two Sum')),
	('Forgot to update the write position after finding a new value.', FALSE, '2026-08-02 09:45:00', (SELECT problem_id FROM problems WHERE title = 'Remove Duplicates from Sorted Array')),
	('Two-pointer solution passed after testing empty and single-element arrays.', TRUE, '2026-08-02 10:20:00', (SELECT problem_id FROM problems WHERE title = 'Remove Duplicates from Sorted Array')),
	('Brute-force substring checks timed out on repeated characters.', FALSE, '2026-08-03 11:00:00', (SELECT problem_id FROM problems WHERE title = 'Longest Palindromic Substring')),
	('Expanded around centers and handled both odd and even lengths.', TRUE, '2026-08-03 11:45:00', (SELECT problem_id FROM problems WHERE title = 'Longest Palindromic Substring')),
	('Merge logic passed after sorting intervals by their starting value.', TRUE, '2026-08-04 14:00:00', (SELECT problem_id FROM problems WHERE title = 'Merge Intervals')),
	('The stack approach is clear, but the partition boundaries still need review.', FALSE, '2026-08-05 15:30:00', (SELECT problem_id FROM problems WHERE title = 'Daily Temperatures')),
	('Need another attempt on the binary-search partition solution.', FALSE, '2026-08-06 16:00:00', (SELECT problem_id FROM problems WHERE title = 'Median of Two Sorted Arrays'));
