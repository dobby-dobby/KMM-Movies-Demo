import SwiftUI
import shared

struct ContentView: View {
	@State private var logMessages: [String] = []
	
	var body: some View {
		VStack {
			Text("KMM Data Logger")
				.font(.title)
				.padding()
			
			ScrollView {
				LazyVStack(alignment: .leading, spacing: 8) {
					ForEach(logMessages, id: \.self) { message in
						Text(message)
							.font(.system(.caption, design: .monospaced))
							.padding(.horizontal)
					}
				}
			}
			.frame(maxWidth: .infinity, maxHeight: .infinity)
			.background(Color.black.opacity(0.1))
		}
		.onAppear {
			startLogging()
		}
	}
	
	private func startLogging() {
		logMessages.append("🚀 Starting KMM data logging...")
		
		// Initialize Koin
		KoinKt.doInitKoin()
		logMessages.append("✅ Koin initialized")
		
		// Test movie repository
		testMovieRepository()
	}
	
	private func testMovieRepository() {
		logMessages.append("📽️ Testing Movie Repository...")
		
		// Get movie repository from Koin
		let movieRepository = KoinHelper().movieRepository
		logMessages.append("✅ Movie Repository obtained from Koin")
		
		// Test trending movies
		Task {
			do {
				let flow = movieRepository.getTrendingMovies()
				for try await result in flow {
					switch result {
					case let success as ResultSuccess:
						let movies = success.data
						await MainActor.run {
							logMessages.append("🎬 Fetched \(movies.count) trending movies")
							movies.forEach { movie in
								logMessages.append("   📺 \(movie.title) (ID: \(movie.id))")
							}
						}
					case let failure as ResultFailure:
						await MainActor.run {
							logMessages.append("❌ Error fetching trending movies: \(failure.exception.message ?? "Unknown error")")
						}
					default:
						await MainActor.run {
							logMessages.append("⚠️ Unknown result type")
						}
					}
				}
			} catch {
				await MainActor.run {
					logMessages.append("❌ Exception: \(error.localizedDescription)")
				}
			}
		}
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}