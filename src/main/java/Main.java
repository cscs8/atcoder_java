import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.BooleanUtils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class Main {

	// ヘッダ
	@NoArgsConstructor
	@AllArgsConstructor
	class Header {
		// 道路
		private Integer roadCount;
		// 交差点
		private Integer intersectionCount;
		// 駐車可能箇所
		private Integer parkingCount;
		// 配達先
		private Integer deliveryCount;
		// クエリ
		private Integer queryCount;

		List<Integer> parseList(final List<String> list) {
			final List<Integer> result = new ArrayList<Integer>();
			list.stream()
					.map(Integer::parseInt)
					.forEach(result::add);
			return result;
		}

		void of(final List<String> list) {
			final List<Integer> list_ = parseList(list);
			roadCount = list_.get(0);
			intersectionCount = list_.get(1);
			parkingCount = list_.get(2);
			deliveryCount = list_.get(3);
			queryCount = list_.get(4);
		}
	}

	// 情報
	interface Informations {
		void of(final List<String> list);
	}

	// 道路情報
	@NoArgsConstructor
	@AllArgsConstructor
	class Road implements Informations {
		// 道路ID
		private String id;
		// メートル
		private Integer m;
		// フラグ 0:車両, 1:台車
		private Integer flag;

		@Override
		public void of(final List<String> list) {
			id = list.get(0);
			m = Integer.parseInt(list.get(1));
			flag = Integer.parseInt(list.get(2));
		}

	}

	// 交差点
	@NoArgsConstructor
	@AllArgsConstructor
	class Intersection implements Informations {
		// 交差点ID
		private String id;
		// 進入元道路ID
		private String in;
		// 進入先道路ID
		private String out;

		@Override
		public void of(final List<String> list) {
			id = list.get(0);
			in = list.get(1);
			out = list.get(2);
		}
	}

	// 駐車可能箇所
	@NoArgsConstructor
	@AllArgsConstructor
	class Parking implements Informations {
		// 駐車箇所ID
		private String id;
		// 道路ID
		private String roadId;
		// 端点Sからの距離
		private Integer m;

		@Override
		public void of(final List<String> list) {
			id = list.get(0);
			roadId = list.get(1);
			m = Integer.parseInt(list.get(2));
		}
	}

	// 配達先
	@NoArgsConstructor
	@AllArgsConstructor
	class Delivery implements Informations {
		// 配達先ID
		private String id;
		// 道路ID
		private String roadId;
		// 端点Sからの距離
		private String m;

		@Override
		public void of(final List<String> list) {
			id = list.get(0);
			roadId = list.get(1);
			m = list.get(2);
		}
	}

	// クエリ
	@NoArgsConstructor
	@AllArgsConstructor
	class Query implements Informations {
		// タイプ 0:車両, 1:台車
		private Integer type;
		// 出発地点
		private String start;
		// 到着地点
		private String end;

		@Override
		public void of(final List<String> list) {
			type = Integer.parseInt(list.get(0));
			start = list.get(1);
			end = list.get(2);
		}

		// 車両か
		boolean isCar() {
			return !isDolly();
		}

		// 台車か
		boolean isDolly() {
			return BooleanUtils.toBoolean(type);
		}
	}

	// ヘッダ
	private Header header;
	// 道路情報
	private List<Road> roads;
	// 交差点情報
	private List<Intersection> intersections;
	// 駐車可能箇所情報
	private List<Parking> parkings;
	// 配達先情報
	private List<Delivery> deliveries;
	// クエリ情報
	private List<Query> queries;

	// 読み込み
	private void initRead(final Scanner sc) {
		createHeader(readSplitList(sc));
		createRoads(sc);
		createIntersections(sc);
		createParkings(sc);
		createDeliveries(sc);
		createQueries(sc);
	}

	private List<String> readSplitList(final Scanner sc) {
		String in = sc.nextLine();
		return Arrays.asList(in.split(" "));

	}

	// ヘッダ
	private void createHeader(final List<String> list) {
		final Header header = new Header();
		header.of(list);
		this.header = header;
	}

	// 道路
	private void createRoads(final Scanner sc) {
		this.roads = new ArrayList<Road>();
		for (int i = 0; i < header.roadCount; i++) {
			createRoad(readSplitList(sc));
		}
	}

	private void createRoad(final List<String> list) {
		final Road road = new Road();
		road.of(list);
		this.roads.add(road);
	}

	// 交差点
	private void createIntersections(final Scanner sc) {
		this.intersections = new ArrayList<Intersection>();
		for (int i = 0; i < header.intersectionCount; i++) {
			createIntersection(readSplitList(sc));
		}
	}

	private void createIntersection(final List<String> list) {
		final Intersection intersection = new Intersection();
		intersection.of(list);
		this.intersections.add(intersection);
	}

	// パーキング
	private void createParkings(final Scanner sc) {
		this.parkings = new ArrayList<Parking>();
		for (int i = 0; i < header.parkingCount; i++) {
			createParking(readSplitList(sc));
		}
	}

	private void createParking(final List<String> list) {
		final Parking parking = new Parking();
		parking.of(list);
		this.parkings.add(parking);
	}

	// 配達先
	private void createDeliveries(final Scanner sc) {
		this.deliveries = new ArrayList<Delivery>();
		for (int i = 0; i < header.deliveryCount; i++) {
			createDelivery(readSplitList(sc));
		}
	}

	private void createDelivery(final List<String> list) {
		final Delivery delivery = new Delivery();
		delivery.of(list);
		this.deliveries.add(delivery);
	}

	// クエリ
	private void createQueries(final Scanner sc) {
		this.queries = new ArrayList<Query>();
		for (int i = 0; i < header.queryCount; i++) {
			createQuery(readSplitList(sc));
		}
	}

	private void createQuery(final List<String> list) {
		final Query query = new Query();
		query.of(list);
		this.queries.add(query);
	}

	// メイン
	public static void main(String[] args) {
		final Main main = new Main();
		try (Scanner sc = new Scanner(System.in)) {
			main.initRead(sc);
		}
		System.out.println(main.header);
		System.out.println(main.roads);
		System.out.println(main.intersections);
		System.out.println(main.parkings);
		System.out.println(main.queries);
	}

}
