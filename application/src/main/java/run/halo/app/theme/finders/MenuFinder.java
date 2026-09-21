package run.halo.app.theme.finders;

import java.util.Collection;
import java.util.Objects;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import run.halo.app.infra.exception.NotFoundException;
import run.halo.app.theme.finders.vo.MenuVo;

/**
 * A finder for {@link run.halo.app.core.extension.Menu}.
 *
 * @author guqing
 * @since 2.0.0
 */
public interface MenuFinder {

    Mono<MenuVo> getByName(String name);

    default Flux<MenuVo> getByNames(Collection<String> names) {
        if (names == null || names.isEmpty()) {
            return Flux.empty();
        }
        return Flux.fromIterable(names)
                .filter(Objects::nonNull)
                .distinct()
                .concatMap(name -> getByName(name)
                        .onErrorResume(NotFoundException.class, error -> Mono.empty()));
    }

    Mono<MenuVo> getPrimary();
}
